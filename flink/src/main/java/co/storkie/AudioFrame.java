/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package co.storkie;

import org.apache.avro.specific.SpecificData;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class AudioFrame extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -6527291589507557182L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"AudioFrame\",\"namespace\":\"co.storkie\",\"fields\":[{\"name\":\"start_ts\",\"type\":\"double\"},{\"name\":\"end_ts\",\"type\":\"double\"},{\"name\":\"data\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"array\",\"items\":\"double\"}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public double start_ts;
  @Deprecated public double end_ts;
  @Deprecated public java.util.List<java.util.List<java.lang.Double>> data;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public AudioFrame() {}

  /**
   * All-args constructor.
   * @param start_ts The new value for start_ts
   * @param end_ts The new value for end_ts
   * @param data The new value for data
   */
  public AudioFrame(java.lang.Double start_ts, java.lang.Double end_ts, java.util.List<java.util.List<java.lang.Double>> data) {
    this.start_ts = start_ts;
    this.end_ts = end_ts;
    this.data = data;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return start_ts;
    case 1: return end_ts;
    case 2: return data;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: start_ts = (java.lang.Double)value$; break;
    case 1: end_ts = (java.lang.Double)value$; break;
    case 2: data = (java.util.List<java.util.List<java.lang.Double>>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'start_ts' field.
   * @return The value of the 'start_ts' field.
   */
  public java.lang.Double getStartTs() {
    return start_ts;
  }

  /**
   * Sets the value of the 'start_ts' field.
   * @param value the value to set.
   */
  public void setStartTs(java.lang.Double value) {
    this.start_ts = value;
  }

  /**
   * Gets the value of the 'end_ts' field.
   * @return The value of the 'end_ts' field.
   */
  public java.lang.Double getEndTs() {
    return end_ts;
  }

  /**
   * Sets the value of the 'end_ts' field.
   * @param value the value to set.
   */
  public void setEndTs(java.lang.Double value) {
    this.end_ts = value;
  }

  /**
   * Gets the value of the 'data' field.
   * @return The value of the 'data' field.
   */
  public java.util.List<java.util.List<java.lang.Double>> getData() {
    return data;
  }

  /**
   * Sets the value of the 'data' field.
   * @param value the value to set.
   */
  public void setData(java.util.List<java.util.List<java.lang.Double>> value) {
    this.data = value;
  }

  /**
   * Creates a new AudioFrame RecordBuilder.
   * @return A new AudioFrame RecordBuilder
   */
  public static co.storkie.AudioFrame.Builder newBuilder() {
    return new co.storkie.AudioFrame.Builder();
  }

  /**
   * Creates a new AudioFrame RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new AudioFrame RecordBuilder
   */
  public static co.storkie.AudioFrame.Builder newBuilder(co.storkie.AudioFrame.Builder other) {
    return new co.storkie.AudioFrame.Builder(other);
  }

  /**
   * Creates a new AudioFrame RecordBuilder by copying an existing AudioFrame instance.
   * @param other The existing instance to copy.
   * @return A new AudioFrame RecordBuilder
   */
  public static co.storkie.AudioFrame.Builder newBuilder(co.storkie.AudioFrame other) {
    return new co.storkie.AudioFrame.Builder(other);
  }

  /**
   * RecordBuilder for AudioFrame instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<AudioFrame>
    implements org.apache.avro.data.RecordBuilder<AudioFrame> {

    private double start_ts;
    private double end_ts;
    private java.util.List<java.util.List<java.lang.Double>> data;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(co.storkie.AudioFrame.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.start_ts)) {
        this.start_ts = data().deepCopy(fields()[0].schema(), other.start_ts);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.end_ts)) {
        this.end_ts = data().deepCopy(fields()[1].schema(), other.end_ts);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.data)) {
        this.data = data().deepCopy(fields()[2].schema(), other.data);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing AudioFrame instance
     * @param other The existing instance to copy.
     */
    private Builder(co.storkie.AudioFrame other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.start_ts)) {
        this.start_ts = data().deepCopy(fields()[0].schema(), other.start_ts);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.end_ts)) {
        this.end_ts = data().deepCopy(fields()[1].schema(), other.end_ts);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.data)) {
        this.data = data().deepCopy(fields()[2].schema(), other.data);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'start_ts' field.
      * @return The value.
      */
    public java.lang.Double getStartTs() {
      return start_ts;
    }

    /**
      * Sets the value of the 'start_ts' field.
      * @param value The value of 'start_ts'.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder setStartTs(double value) {
      validate(fields()[0], value);
      this.start_ts = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'start_ts' field has been set.
      * @return True if the 'start_ts' field has been set, false otherwise.
      */
    public boolean hasStartTs() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'start_ts' field.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder clearStartTs() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'end_ts' field.
      * @return The value.
      */
    public java.lang.Double getEndTs() {
      return end_ts;
    }

    /**
      * Sets the value of the 'end_ts' field.
      * @param value The value of 'end_ts'.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder setEndTs(double value) {
      validate(fields()[1], value);
      this.end_ts = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'end_ts' field has been set.
      * @return True if the 'end_ts' field has been set, false otherwise.
      */
    public boolean hasEndTs() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'end_ts' field.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder clearEndTs() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'data' field.
      * @return The value.
      */
    public java.util.List<java.util.List<java.lang.Double>> getData() {
      return data;
    }

    /**
      * Sets the value of the 'data' field.
      * @param value The value of 'data'.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder setData(java.util.List<java.util.List<java.lang.Double>> value) {
      validate(fields()[2], value);
      this.data = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'data' field has been set.
      * @return True if the 'data' field has been set, false otherwise.
      */
    public boolean hasData() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'data' field.
      * @return This builder.
      */
    public co.storkie.AudioFrame.Builder clearData() {
      data = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    public AudioFrame build() {
      try {
        AudioFrame record = new AudioFrame();
        record.start_ts = fieldSetFlags()[0] ? this.start_ts : (java.lang.Double) defaultValue(fields()[0]);
        record.end_ts = fieldSetFlags()[1] ? this.end_ts : (java.lang.Double) defaultValue(fields()[1]);
        record.data = fieldSetFlags()[2] ? this.data : (java.util.List<java.util.List<java.lang.Double>>) defaultValue(fields()[2]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  private static final org.apache.avro.io.DatumWriter
    WRITER$ = new org.apache.avro.specific.SpecificDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  private static final org.apache.avro.io.DatumReader
    READER$ = new org.apache.avro.specific.SpecificDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
