import io
import threading
import time

import fastavro
from fastavro import reader

import matplotlib.pyplot as plt
from kafka import KafkaProducer, KafkaConsumer
from matplotlib import animation
import numpy as np

from scipy.ndimage import uniform_filter1d


class ChartPlotter:
    lastPlotTime = time.time()

    no_of_freqs = 1024 * 4

    def __init__(self, signal_processor):
        self.current_record = None
        self.fig, (left_freq, right_freq) = plt.subplots(2)
        self.signal_processor = signal_processor

        left_freq.set_ylim((0, 0.06))
        right_freq.set_ylim((0, 0.06))

        # left_freq.set_ylim((-0.2, 0.2))
        # right_freq.set_ylim((-0.2, 0.2))

        self.lf, = left_freq.plot(np.arange(0, self.no_of_freqs) * 44100.0 / 2.0 / self.no_of_freqs,
                                  np.zeros(self.no_of_freqs))
        self.rf, = right_freq.plot(np.arange(0, self.no_of_freqs) * 44100.0 / 2.0 / self.no_of_freqs,
                                   np.zeros(self.no_of_freqs))

        left_freq.set_xscale('log')
        right_freq.set_xscale('log')

        self.ani = animation.FuncAnimation(self.fig, self.draw, interval=10, blit=True)


        # self.lf, = left_freq.plot([], [])
        # self.rf, = right_freq.plot([], [])

    def new_data(self, record):
        self.current_record = record

    def draw(self, i):
        if self.current_record.get('fft') is not None:
            freqs = np.arange(0, self.no_of_freqs) * self.current_record['bucketSize']

            self.lf.set_data(freqs, self.current_record['fft'][0])
            self.rf.set_data(freqs, self.current_record['fft'][1])

            # print("ploting FPS: %s" % (1 / (time.time() - self.lastPlotTime)))
            self.lastPlotTime = time.time()

            return self.rf, self.lf

    def start(self):
        plt.show()


class FftPlotter:

    def __init__(self, listeners, bootstrap='192.168.1.111:9092'):
        self.client = KafkaConsumer(
            'audio-fft',
            bootstrap_servers=bootstrap,
            client_id='app',
            api_version=(0, 10, 1)
        )
        self.listeners = listeners

    def do(self):
        schema = fastavro.schema.load_schema('avsc/FFTResult.avsc')
        for msg in self.client:
            bytes_reader = io.BytesIO(msg.value)
            for record in reader(bytes_reader, schema):
                for listener in self.listeners:
                    listener.new_data(record)

                print(time.time() * 1000 - record['start_ts'])

    def start(self):
        x = threading.Thread(target=self.do)
        x.start()


class SignalProcessor:

    def analyze(self, sampling_freq, samples):
        (left, right) = samples.transpose()
        left_fft = np.array_split(np.abs(np.fft.fft(left, norm='ortho')), 2)[0]
        right_fft = np.array_split(np.abs(np.fft.fft(right, norm='ortho')), 2)[0]

        freqs = np.split(np.fft.fftfreq(int(samples.size / 2)) * sampling_freq, 2)[0]

        return freqs, uniform_filter1d(left_fft, size=2), uniform_filter1d(right_fft, size=10)



if __name__ == '__main__':
    plotter = ChartPlotter(SignalProcessor())

    kafkareceiver = FftPlotter([plotter])
    kafkareceiver.start()

    plotter.start()
