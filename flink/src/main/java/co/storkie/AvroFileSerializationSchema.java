package co.storkie;

import org.apache.avro.Schema;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.specific.SpecificRecord;
import org.apache.flink.api.common.serialization.SerializationSchema;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AvroFileSerializationSchema<T extends SpecificRecord> implements SerializationSchema<T> {

    private transient Schema schema;
    private transient DataFileWriter<T> dataFileWriter;

    public AvroFileSerializationSchema() {
    }

    @Override
    public byte[] serialize(T elem) {
        try {
            ensureInitialized(elem.getSchema());

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            dataFileWriter.create(elem.getSchema(), out);
            dataFileWriter.append(elem);
            dataFileWriter.flush();
            dataFileWriter.close();
            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void ensureInitialized(Schema schema) {
        this.schema = schema;
        this.dataFileWriter = new DataFileWriter<T>(new GenericDatumWriter<T>(this.schema));
    }
}
