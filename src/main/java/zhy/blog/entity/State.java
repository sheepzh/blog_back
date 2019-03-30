package zhy.blog.entity;

public class State extends BaseEntity {
    String itemKey;
    String itemValue;

    public String getItemKey() {
        return itemKey;
    }

    public State setItemKey(String itemKey) {
        this.itemKey = itemKey;
        return this;
    }

    public String getItemValue() {
        return itemValue;
    }

    public State setItemValue(String itemValue) {
        this.itemValue = itemValue;
        return this;
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
