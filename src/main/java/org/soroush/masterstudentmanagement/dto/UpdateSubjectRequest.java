
package org.soroush.masterstudentmanagement.dto;

public record UpdateSubjectRequest(
        String title,
        Integer units,
        Long masterId
) {
}
