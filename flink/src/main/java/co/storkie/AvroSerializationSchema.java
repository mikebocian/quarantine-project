package co.storkie;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.serialization.SerializationSchema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroSerializationSchema<T extends SpecificRecord> implements SerializationSchema<T> {

    private transient Schema schema;
    private transient GenericDatumWriter<T> dataFileWriter;

    public AvroSerializationSchema() {
    }

    @Override
    public byte[] serialize(T elem) {
        try {
            ensureInitialized(elem.getSchema());

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            dataFileWriter.write(elem, EncoderFactory.get().binaryEncoder(out, null));

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureInitialized(Schema schema) {
        this.schema = schema;
        this.dataFileWriter = new GenericDatumWriter<T>(this.schema);
    }
}
