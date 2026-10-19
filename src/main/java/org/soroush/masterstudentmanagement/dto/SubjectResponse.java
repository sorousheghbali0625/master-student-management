
package org.soroush.masterstudentmanagement.dto;

public record SubjectResponse(
        Long id,
        String title,
        Integer units,
        Long masterId
) {
}
