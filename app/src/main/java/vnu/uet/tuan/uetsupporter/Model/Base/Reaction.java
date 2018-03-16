package vnu.uet.tuan.uetsupporter.Model.Base;

import java.io.Serializable;

/**
 * Created by FRAMGIA\vu.minh.tuan on 16/03/2018.
 */



public class Reaction implements IReactable,Serializable{
    private int length;
    private boolean isReact;

    {
        length =0;
        isReact=false;
    }

    public Reaction(int length, boolean isReact) {
        this.length = length;
        this.isReact = isReact;
    }

    public Reaction() {

    }


    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public boolean getIsReact() {
        return this.isReact;
    }

    @Override
    public void setIsReact(boolean isReact) {
        this.isReact = isReact;
    }
}
