package io.mikael.convert.json;

abstract class JsonConverterSource {

    protected abstract void object();

    protected abstract void primitive();

}

class JsonConverterTarget {

}

public class JsonConverter {

    protected JsonConverterSource source;

    protected JsonConverterTarget target;

}
