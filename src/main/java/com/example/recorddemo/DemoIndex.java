package com.example.recorddemo;

import com.intellij.openapi.fileTypes.PlainTextFileType;
import com.intellij.util.indexing.*;
import com.intellij.util.io.DataExternalizer;
import com.intellij.util.io.EnumeratorStringDescriptor;
import com.intellij.util.io.KeyDescriptor;
import org.jetbrains.annotations.NotNull;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class DemoIndex extends FileBasedIndexExtension<String, DemoIndex.IndexData> {

    private static final ID<String, IndexData> NAME = ID.create("com.example.recorddemo.DemoIndex");

    @Override
    public @NotNull ID<String, IndexData> getName() {
        return NAME;
    }

    @Override
    public @NotNull DataIndexer<String, IndexData, FileContent> getIndexer() {
        return inputData -> Map.of(inputData.getFileName(), new IndexData(ThreadLocalRandom.current().nextInt()));
    }

    @Override
    public @NotNull KeyDescriptor<String> getKeyDescriptor() {
        return EnumeratorStringDescriptor.INSTANCE;
    }

    @Override
    public @NotNull DataExternalizer<IndexData> getValueExternalizer() {
        return new DataExternalizer<>() {
            @Override
            public void save(@NotNull DataOutput out, IndexData value) throws IOException {
                out.writeInt(value.value());
            }

            @Override
            public IndexData read(@NotNull DataInput in) throws IOException {
                return new IndexData(in.readInt());
            }
        };
    }

    @Override
    public int getVersion() {
        return 0;
    }

    @Override
    public FileBasedIndex.@NotNull InputFilter getInputFilter() {
        return new DefaultFileTypeSpecificInputFilter(PlainTextFileType.INSTANCE);
    }

    @Override
    public boolean dependsOnFileContent() {
        return false;
    }

    record IndexData(int value) {}
}
