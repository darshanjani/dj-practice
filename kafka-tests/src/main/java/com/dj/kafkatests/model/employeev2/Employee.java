/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.dj.kafkatests.model.employeev2;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Employee extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -2279979079008564230L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Employee\",\"namespace\":\"com.dj.kafkatests.model.employeev2\",\"fields\":[{\"name\":\"fName\",\"type\":\"string\"},{\"name\":\"lName\",\"type\":\"string\"},{\"name\":\"phoneNumber\",\"type\":\"string\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<Employee> ENCODER =
      new BinaryMessageEncoder<Employee>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<Employee> DECODER =
      new BinaryMessageDecoder<Employee>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<Employee> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<Employee> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<Employee>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this Employee to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a Employee from a ByteBuffer. */
  public static Employee fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  @Deprecated public java.lang.CharSequence fName;
  @Deprecated public java.lang.CharSequence lName;
  @Deprecated public java.lang.CharSequence phoneNumber;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public Employee() {}

  /**
   * All-args constructor.
   * @param fName The new value for fName
   * @param lName The new value for lName
   * @param phoneNumber The new value for phoneNumber
   */
  public Employee(java.lang.CharSequence fName, java.lang.CharSequence lName, java.lang.CharSequence phoneNumber) {
    this.fName = fName;
    this.lName = lName;
    this.phoneNumber = phoneNumber;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return fName;
    case 1: return lName;
    case 2: return phoneNumber;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: fName = (java.lang.CharSequence)value$; break;
    case 1: lName = (java.lang.CharSequence)value$; break;
    case 2: phoneNumber = (java.lang.CharSequence)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'fName' field.
   * @return The value of the 'fName' field.
   */
  public java.lang.CharSequence getFName() {
    return fName;
  }

  /**
   * Sets the value of the 'fName' field.
   * @param value the value to set.
   */
  public void setFName(java.lang.CharSequence value) {
    this.fName = value;
  }

  /**
   * Gets the value of the 'lName' field.
   * @return The value of the 'lName' field.
   */
  public java.lang.CharSequence getLName() {
    return lName;
  }

  /**
   * Sets the value of the 'lName' field.
   * @param value the value to set.
   */
  public void setLName(java.lang.CharSequence value) {
    this.lName = value;
  }

  /**
   * Gets the value of the 'phoneNumber' field.
   * @return The value of the 'phoneNumber' field.
   */
  public java.lang.CharSequence getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Sets the value of the 'phoneNumber' field.
   * @param value the value to set.
   */
  public void setPhoneNumber(java.lang.CharSequence value) {
    this.phoneNumber = value;
  }

  /**
   * Creates a new Employee RecordBuilder.
   * @return A new Employee RecordBuilder
   */
  public static com.dj.kafkatests.model.employeev2.Employee.Builder newBuilder() {
    return new com.dj.kafkatests.model.employeev2.Employee.Builder();
  }

  /**
   * Creates a new Employee RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new Employee RecordBuilder
   */
  public static com.dj.kafkatests.model.employeev2.Employee.Builder newBuilder(com.dj.kafkatests.model.employeev2.Employee.Builder other) {
    return new com.dj.kafkatests.model.employeev2.Employee.Builder(other);
  }

  /**
   * Creates a new Employee RecordBuilder by copying an existing Employee instance.
   * @param other The existing instance to copy.
   * @return A new Employee RecordBuilder
   */
  public static com.dj.kafkatests.model.employeev2.Employee.Builder newBuilder(com.dj.kafkatests.model.employeev2.Employee other) {
    return new com.dj.kafkatests.model.employeev2.Employee.Builder(other);
  }

  /**
   * RecordBuilder for Employee instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Employee>
    implements org.apache.avro.data.RecordBuilder<Employee> {

    private java.lang.CharSequence fName;
    private java.lang.CharSequence lName;
    private java.lang.CharSequence phoneNumber;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(com.dj.kafkatests.model.employeev2.Employee.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.fName)) {
        this.fName = data().deepCopy(fields()[0].schema(), other.fName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lName)) {
        this.lName = data().deepCopy(fields()[1].schema(), other.lName);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.phoneNumber)) {
        this.phoneNumber = data().deepCopy(fields()[2].schema(), other.phoneNumber);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing Employee instance
     * @param other The existing instance to copy.
     */
    private Builder(com.dj.kafkatests.model.employeev2.Employee other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.fName)) {
        this.fName = data().deepCopy(fields()[0].schema(), other.fName);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.lName)) {
        this.lName = data().deepCopy(fields()[1].schema(), other.lName);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.phoneNumber)) {
        this.phoneNumber = data().deepCopy(fields()[2].schema(), other.phoneNumber);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'fName' field.
      * @return The value.
      */
    public java.lang.CharSequence getFName() {
      return fName;
    }

    /**
      * Sets the value of the 'fName' field.
      * @param value The value of 'fName'.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder setFName(java.lang.CharSequence value) {
      validate(fields()[0], value);
      this.fName = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'fName' field has been set.
      * @return True if the 'fName' field has been set, false otherwise.
      */
    public boolean hasFName() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'fName' field.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder clearFName() {
      fName = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'lName' field.
      * @return The value.
      */
    public java.lang.CharSequence getLName() {
      return lName;
    }

    /**
      * Sets the value of the 'lName' field.
      * @param value The value of 'lName'.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder setLName(java.lang.CharSequence value) {
      validate(fields()[1], value);
      this.lName = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'lName' field has been set.
      * @return True if the 'lName' field has been set, false otherwise.
      */
    public boolean hasLName() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'lName' field.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder clearLName() {
      lName = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'phoneNumber' field.
      * @return The value.
      */
    public java.lang.CharSequence getPhoneNumber() {
      return phoneNumber;
    }

    /**
      * Sets the value of the 'phoneNumber' field.
      * @param value The value of 'phoneNumber'.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder setPhoneNumber(java.lang.CharSequence value) {
      validate(fields()[2], value);
      this.phoneNumber = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'phoneNumber' field has been set.
      * @return True if the 'phoneNumber' field has been set, false otherwise.
      */
    public boolean hasPhoneNumber() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'phoneNumber' field.
      * @return This builder.
      */
    public com.dj.kafkatests.model.employeev2.Employee.Builder clearPhoneNumber() {
      phoneNumber = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Employee build() {
      try {
        Employee record = new Employee();
        record.fName = fieldSetFlags()[0] ? this.fName : (java.lang.CharSequence) defaultValue(fields()[0]);
        record.lName = fieldSetFlags()[1] ? this.lName : (java.lang.CharSequence) defaultValue(fields()[1]);
        record.phoneNumber = fieldSetFlags()[2] ? this.phoneNumber : (java.lang.CharSequence) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<Employee>
    WRITER$ = (org.apache.avro.io.DatumWriter<Employee>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<Employee>
    READER$ = (org.apache.avro.io.DatumReader<Employee>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
