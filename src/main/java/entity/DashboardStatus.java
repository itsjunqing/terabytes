package entity;

/**
 * An DashboardStatus Enum class representing the statuses of a Dashboard
 *
 * MAX: Dashboard is fulled (has reached the limit of max Contracts)
 * OPEN: An Open Bid is still on going
 * CLOSE: A Close Bid is still on going
 * PASS: No bids are going
 */
public enum DashboardStatus {
    MAX,
    OPEN,
    CLOSE,
    PASS
}
