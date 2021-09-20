package io.github.phantomstr.testing.tool.comparison.utils;

import lombok.Setter;

public abstract class SelfContainer<SELF extends SelfContainer<SELF>> {
    @Setter
    protected SELF myself;

    public SelfContainer(SELF myself) {
        this.myself = myself;
        if (myself != null) {
            myself.setMyself(myself);
        }
    }

}
