package com.nikolay.r3s.data.repositories;

import com.nikolay.r3s.models.Entry;

public class EntrySpecificationBySubscriptionId implements ISpecification<Entry> {
    private int subscriptionId;

    public EntrySpecificationBySubscriptionId(int id) {
        this.setSubscriptionId(id);
    }

    private void setSubscriptionId(int id) {
        this.subscriptionId = id;
    }

    private int getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public boolean specified(Entry entry) {
        return entry.hasSubscriptionId(this.getSubscriptionId());
    }
}
