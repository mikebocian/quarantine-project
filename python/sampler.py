import argparse
import io
import time

import fastavro
import numpy
import soundcard as sc
from kafka import KafkaProducer


class Sampler:
    samplingFreq = 44100

    def __init__(self, topic, interface_no, freq_buffer=5 * samplingFreq, bootstrap='localhost:9092'):
        self.freq_buffer = freq_buffer
        self.loopback = sc.all_microphones(True)[interface_no]
        self.producer = KafkaProducer(
            bootstrap_servers=bootstrap,
            client_id='app',
            api_version=(0, 10, 1)
        )
        self.topic = topic

    def record(self):
        schema = fastavro.schema.load_schema('avsc/AudioFrame.avsc')

        timestamp = time.time()
        frames = []

        with self.loopback.recorder(self.samplingFreq, blocksize=256) as rec:
            while True:
                start = time.time() * 1000.0
                samples = rec.record(256)
                frame = {
                    'data': samples.tolist(),
                    'start_ts': start,
                    'end_ts': time.time() * 1000.0
                }
                fo = io.BytesIO()
                fastavro.schemaless_writer(fo, schema, frame)

                self.producer.send(self.topic, fo.getvalue())

                frames = frames + [frame]
                if time.time() - timestamp > 1:
                    no_of_frames = len(frames)
                    no_of_samples = sum(map(lambda f: len(f['data']), frames))
                    total_volume = numpy.sum(numpy.abs(list(map(lambda x: x['data'], frames))))
                    print('stats per 1s: no_of_frames={}, total_no_of_samples={}, total_volume={}'
                          .format(no_of_frames, no_of_samples, total_volume))
                    timestamp = time.time()
                    frames = []


if __name__ == '__main__':
    my_parser = argparse.ArgumentParser(
        formatter_class=argparse.RawDescriptionHelpFormatter,
        description='The program samples audio interface and sends the data to Kafka.\n\n'
                    'List all the available audio interfaces run: `sampler.py listInterfaces`')

    my_parser.add_argument('--topic',
                           help='Kafka topic to publish messages ( default: audio ).', default='audio', type=str)
    my_parser.add_argument('--interfaceNo',
                           help='The number of the audio interface to sample.', type=int)
    my_parser.add_argument('mode',
                           help='`stream` to start streaming, `listInterfaces` to list all available interfaces',
                           default='stream', choices=['stream', 'listInterfaces'])

    args = my_parser.parse_args()

    if args.mode == 'listInterfaces':
        print("Default speaker is: {}\n".format(sc.default_speaker()))
        print("Available audio interfaces are:")
        for num, name in enumerate(sc.all_microphones(True), start=0):
            print('{}: {}'.format(num, name))
    else:
        print("ctrl+c to interrupt")
        Sampler(args.topic, args.interfaceNo).record()
    #
    # sampler = Sampler("audio")
    # sampler.record()
