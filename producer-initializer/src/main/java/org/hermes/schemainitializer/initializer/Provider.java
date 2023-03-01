package org.hermes.schemainitializer.initializer;

import org.apache.avro.specific.SpecificRecord;

public interface Provider {

    SpecificRecord getRecord();

    String getTopic();
}
