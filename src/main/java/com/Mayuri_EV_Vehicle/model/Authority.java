package com.Mayuri_EV_Vehicle.model;

public enum Authority {
	
	A0001("Users", "users", "/app/users.html", "nc-single-02", 3),
    A0002("Create User", null, null, null, 0),
    A0003("Change User Status", null, null, null, 0),
    A0004("Assign Role to User", null, null, null, 0),
    A0005("Roles", "roles", "/app/roles.html", "nc-badge", 4),
    A0006("Assign Permission to Role", null, null, null, 0),

    A0007("Leads", "leads", "/app/leads.html", "", 1),
    A0008("Upload Lead", null, null, null, 0),
    A0009("Lead Distribution", "leads-distribution", "/app/lead-distribution.html", "", 2),
    A0010("Assign Lead", null, null, null, 0),

    A0011("Pending Leads", "pending-leads", "/app/pending-leads.html", "", 5),
    A0012("Parked Leads", "parked-leads", "/app/parked-leads.html", "", 6),
    A0013("Closed Leads", "closed-leads", "/app/closed-leads.html", "", 7);

    private final String name;
    private final String activeNav;
    private final String url;
    private final String icon;
    private final int displayOrder;

    Authority(String name, String activeNav, String url, String icon, int displayOrder) {
        this.name = name;
        this.activeNav = activeNav;
        this.url = url;
        this.icon = icon;
        this.displayOrder = displayOrder;
    }

    public String getName() {
        return this.name;
    }

    public String getActiveNav() {
        return this.activeNav;
    }

    public String getUrl() {
        return this.url;
    }

    public String getIcon() {
        return this.icon;
    }

    public int getDisplayOrder() {
        return this.displayOrder;
    }

}
