package com.nikolay.r3s.data.repositories;

import com.nikolay.r3s.models.Entry;

public class EntrySpecificationBySubscriptionId implements ISpecification<Entry> {
    private String subscriptionId;

    public EntrySpecificationBySubscriptionId(String id) {
        this.setSubscriptionId(id);
    }

    private void setSubscriptionId(String id) {
        this.subscriptionId = id;
    }

    private String getSubscriptionId() {
        return this.subscriptionId;
    }

    @Override
    public boolean specified(Entry entry) {
        return entry.hasSubscriptionId(this.getSubscriptionId());
    }
}
