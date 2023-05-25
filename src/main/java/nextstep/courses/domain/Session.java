package nextstep.courses.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import nextstep.courses.CannotEnrollException;
import nextstep.users.domain.NsUser;

public class Session {

    private final Long id;

    private final LocalDateTime startDate;

    private final LocalDateTime endDate;

    private final String coverImagePath;

    private final PriceType priceType;

    private final Status status;

    private final Long maximumCapacity;

    private final List<NsUser> users;

    public Session(Long id, LocalDateTime startDate, LocalDateTime endDate, String coverImagePath,
        PriceType priceType, Status status, Long maximumCapacity, List<NsUser> users) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.coverImagePath = coverImagePath;
        this.priceType = priceType;
        this.status = status;
        this.maximumCapacity = maximumCapacity;
        this.users = users;
    }

    public static Session of(Long id, LocalDateTime start, LocalDateTime end, String coverImagePath,
        PriceType priceType, Status status, Long maximumCapacity, List<NsUser> users) {
        return new Session(id, start, end, coverImagePath, priceType, status, maximumCapacity,
            users);
    }

    public void enroll(NsUser user) throws CannotEnrollException {
        if (isNotCurrentlyEnrolling()) {
            throw new CannotEnrollException("모집중인 강의가 아닙니다 : " + status.description());
        }
        if (isExceedingMaximumCapacity()) {
            throw new CannotEnrollException("최대 수강 인원을 초과했습니다.");
        }
        users.add(user);
    }

    public boolean isEnrolled(NsUser user) {
        return users.contains(user);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Session session = (Session) o;
        return Objects.equals(id, session.id) && Objects.equals(startDate,
            session.startDate) && Objects.equals(endDate, session.endDate)
            && Objects.equals(coverImagePath, session.coverImagePath)
            && Objects.equals(priceType, session.priceType) && Objects.equals(
            status, session.status) && Objects.equals(maximumCapacity,
            session.maximumCapacity) && Objects.equals(users, session.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate, coverImagePath, priceType, status,
            maximumCapacity,
            users);
    }

    private boolean isNotCurrentlyEnrolling() {
        return !status.equals(Status.ENROLLING);
    }

    private boolean isExceedingMaximumCapacity() {
        return users.size() >= maximumCapacity;
    }

    public static class Builder {
        private Long id;

        private LocalDateTime startDate;

        private LocalDateTime endDate;

        private String coverImagePath;

        private PriceType priceType;

        private Status status;

        private Long maximumCapacity;

        private List<NsUser> users;

        public Builder() {}

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder startDate(LocalDateTime startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder endDate(LocalDateTime endDate) {
            this.endDate = endDate;
            return this;
        }

        public Builder coverImagePath(String coverImagePath) {
            this.coverImagePath = coverImagePath;
            return this;
        }

        public Builder priceType(PriceType priceType) {
            this.priceType = priceType;
            return this;
        }

        public Builder status(Status status) {
            this.status = status;
            return this;
        }

        public Builder maximumCapacity(Long maximumCapacity) {
            this.maximumCapacity = maximumCapacity;
            return this;
        }

        public Builder users(List<NsUser> users) {
            this.users = users;
            return this;
        }

        public Session build() {
            return new Session(id, startDate, endDate, coverImagePath, priceType, status, maximumCapacity, users);
        }
    }
}