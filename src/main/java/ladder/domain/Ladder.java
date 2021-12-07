package ladder.domain;

import ladder.util.GameUtil;
import ladder.util.RandomStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Ladder {
    private final List<Line> value;

    public Ladder(int width, int height) {
        this(width, height, new RandomStrategy());
    }

    public Ladder(int width, int height, Predicate<Boolean> strategy) {
        this(new LadderHelper(width, height).generate(strategy));
    }

    public Ladder(List<Line> value) {
        validate(value);
        this.value = value;
    }

    private void validate(List<Line> value) {
        GameUtil.requireNonNullOrSizeGreaterThanZero(value);
    }

    public LadderResult result() {
        List<Integer> result = new ArrayList<>();

        IntStream.range(0, this.value.get(0).size())
                .map(this::resultByPosition)
                .forEach(result::add);

        return new LadderResult(result);
    }

    private int resultByPosition(int initPosition) {
        int target = initPosition;
        for (final Line line : this.value) {
            target = line.nextPosition(target);
        }
        return target;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ladder ladder = (Ladder) o;
        return Objects.equals(value, ladder.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return this.value.stream()
                .map(Line::toString)
                .collect(Collectors.joining("\n"));
    }
}
