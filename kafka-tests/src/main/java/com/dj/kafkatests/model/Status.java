/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package com.dj.kafkatests.model;
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public enum Status {
  RETIRED, SALARY, HOURLY, PART_TIME  ;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"enum\",\"name\":\"Status\",\"namespace\":\"com.dj.kafkatests.model\",\"symbols\":[\"RETIRED\",\"SALARY\",\"HOURLY\",\"PART_TIME\"]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
}