package com.parser.ast.fragment;

import com.parser.ast.ASTNode;
import com.parser.ast.expression.primary.ParamMarker;

public class Limit implements ASTNode {
    private final Number offset;
    private final Number size;
    private final ParamMarker offsetP;
    private final ParamMarker sizeP;
    private final boolean nullOffset;

    public Limit(Number offset, Number size) {
        if (offset == null)
            throw new IllegalArgumentException();
        if (size == null)
            throw new IllegalArgumentException();
        this.offset = offset;
        this.size = size;
        this.offsetP = null;
        this.sizeP = null;
        this.nullOffset = false;
    }

    public Limit(Number offset, Number size, boolean nullOffset) {
        if (offset == null)
            throw new IllegalArgumentException();
        if (size == null)
            throw new IllegalArgumentException();
        this.offset = offset;
        this.size = size;
        this.offsetP = null;
        this.sizeP = null;
        this.nullOffset = nullOffset;
    }

    public Limit(Number offset, ParamMarker sizeP, boolean nullOffset) {
        if (offset == null)
            throw new IllegalArgumentException();
        if (sizeP == null)
            throw new IllegalArgumentException();
        this.offset = offset;
        this.size = null;
        this.offsetP = null;
        this.sizeP = sizeP;
        this.nullOffset = nullOffset;
    }

    public Limit(ParamMarker offsetP, Number size) {
        if (offsetP == null)
            throw new IllegalArgumentException();
        if (size == null)
            throw new IllegalArgumentException();
        this.offset = null;
        this.size = size;
        this.offsetP = offsetP;
        this.sizeP = null;
        this.nullOffset = false;
    }

    public Limit(ParamMarker offsetP, ParamMarker sizeP) {
        if (offsetP == null)
            throw new IllegalArgumentException();
        if (sizeP == null)
            throw new IllegalArgumentException();
        this.offset = null;
        this.size = null;
        this.offsetP = offsetP;
        this.sizeP = sizeP;
        this.nullOffset = false;
    }

    public Number getOffset() {
        return offset;
    }

    public Number getSize() {
        return size;
    }

    public ParamMarker getOffsetP() {
        return offsetP;
    }

    public ParamMarker getSizeP() {
        return sizeP;
    }

    public boolean isNullOffset() {
        return nullOffset;
    }

    public String toString() {
        return "Limit{" +
                "offset=" + offset +
                ", size=" + size +
                ", offsetP=" + offsetP +
                ", sizeP=" + sizeP +
                ", nullOffset=" + nullOffset +
                '}';
    }
}
