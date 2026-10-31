package calendar.model.entity;

/**
 * Defines how an edit to a recurring event should be applied.
 *
 * <p>The edit scope determines whether a modification affects only a single
 * occurrence, a portion of the remaining series, or the entire series.</p>
 *
 * <ul>
 *   <li>{@link #ONE} — apply the edit only to the selected occurrence</li>
 *   <li>{@link #THIS_AND_AFTER} — apply the edit to the selected occurrence
 *       and all subsequent occurrences in the series</li>
 *   <li>{@link #ENTIRE_SERIES} — apply the edit to every occurrence in the series</li>
 * </ul>
 */
public enum EditScope {
  /** Edit affects only the single chosen occurrence. */
  ONE,
  /** Edit affects this occurrence and all future occurrences. */
  THIS_AND_AFTER,
  /** Edit affects all occurrences in the recurring series. */
  ENTIRE_SERIES
}
