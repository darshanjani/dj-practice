/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package example.avro;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Manager extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -225555223915265245L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Manager\",\"namespace\":\"example.avro\",\"fields\":[{\"name\":\"manages\",\"type\":{\"type\":\"array\",\"items\":{\"type\":\"record\",\"name\":\"Employee\",\"fields\":[{\"name\":\"name\",\"type\":\"string\"},{\"name\":\"active\",\"type\":\"boolean\"},{\"name\":\"sid\",\"type\":\"int\"},{\"name\":\"ssn\",\"type\":\"long\"},{\"name\":\"salary\",\"type\":\"double\"},{\"name\":\"increment\",\"type\":\"float\"}]}}}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Manager> ENCODER =
      new BinaryMessageEncoder<Manager>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Manager> DECODER =
      new BinaryMessageDecoder<Manager>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Manager> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Manager> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Manager>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Manager to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Manager from a ByteBuffer. */
  public static Manager fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private java.util.List<example.avro.Employee> manages;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Manager() {}

  /**
   * All-args constructor.
   * @param manages The new value for manages
   */
  public Manager(java.util.List<example.avro.Employee> manages) {
    this.manages = manages;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return manages;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: manages = (java.util.List<example.avro.Employee>)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'manages' field.
   * @return The value of the 'manages' field.
   */
  public java.util.List<example.avro.Employee> getManages() {
    return manages;
  }

  /**
   * Sets the value of the 'manages' field.
   * @param value the value to set.
   */
  public void setManages(java.util.List<example.avro.Employee> value) {
    this.manages = value;
  }

  /**
   * Creates a new Manager RecordBuilder.
   * @return A new Manager RecordBuilder
   */
  public static example.avro.Manager.Builder newBuilder() {
    return new example.avro.Manager.Builder();
  }

  /**
   * Creates a new Manager RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Manager RecordBuilder
   */
  public static example.avro.Manager.Builder newBuilder(example.avro.Manager.Builder other) {
    return new example.avro.Manager.Builder(other);
  }

  /**
   * Creates a new Manager RecordBuilder by copying an existing Manager instance.
   * @param other The existing instance to copy.
   * @return A new Manager RecordBuilder
   */
  public static example.avro.Manager.Builder newBuilder(example.avro.Manager other) {
    return new example.avro.Manager.Builder(other);
  }

  /**
   * RecordBuilder for Manager instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Manager>
    implements org.apache.avro.data.RecordBuilder<Manager> {

    private java.util.List<example.avro.Employee> manages;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(example.avro.Manager.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.manages)) {
        this.manages = data().deepCopy(fields()[0].schema(), other.manages);
        fieldSetFlags()[0] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Manager instance
     * @param other The existing instance to copy.
     */
    private Builder(example.avro.Manager other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.manages)) {
        this.manages = data().deepCopy(fields()[0].schema(), other.manages);
        fieldSetFlags()[0] = true;
      }
    }

    /**
      * Gets the value of the 'manages' field.
      * @return The value.
      */
    public java.util.List<example.avro.Employee> getManages() {
      return manages;
    }

    /**
      * Sets the value of the 'manages' field.
      * @param value The value of 'manages'.
      * @return This builder.
      */
    public example.avro.Manager.Builder setManages(java.util.List<example.avro.Employee> value) {
      validate(fields()[0], value);
      this.manages = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'manages' field has been set.
      * @return True if the 'manages' field has been set, false otherwise.
      */
    public boolean hasManages() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'manages' field.
      * @return This builder.
      */
    public example.avro.Manager.Builder clearManages() {
      manages = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Manager build() {
      try {
        Manager record = new Manager();
        record.manages = fieldSetFlags()[0] ? this.manages : (java.util.List<example.avro.Employee>) defaultValue(fields()[0]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Manager>
    WRITER$ = (org.apache.avro.io.DatumWriter<Manager>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Manager>
    READER$ = (org.apache.avro.io.DatumReader<Manager>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
