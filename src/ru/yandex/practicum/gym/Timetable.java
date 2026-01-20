package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, TrainingSession> day = timetable.get(dayOfWeek);
        if (day == null) {
            day = new TreeMap<>();
            timetable.put(dayOfWeek, day);
        }

        day.put(timeOfDay, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> day = timetable.get(dayOfWeek);
        if (day == null) {
            return Collections.emptyList();
        }

        return new ArrayList<>(day.values());
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> day = timetable.get(dayOfWeek);
        if (day == null) {
            return null;
        }

        return day.get(timeOfDay);
    }

    public Map<String, Integer> getCountByCoaches() {
        Map<Coach, Integer> integerMap = new HashMap<>();
        for (TreeMap<TimeOfDay, TrainingSession> value : timetable.values()) {
            for (TrainingSession trainingSession : value.values()) {
                Coach coach = trainingSession.getCoach();
                integerMap.put(coach, integerMap.getOrDefault(coach, 0) + 1);
            }
        }

        List<CounterOfTrainings> counterOfTrainings = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : integerMap.entrySet()) {
            CounterOfTrainings counterOfTrainings1 = new CounterOfTrainings(entry.getKey(), entry.getValue());
            counterOfTrainings.add(counterOfTrainings1);
        }

        Collections.sort(counterOfTrainings, new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return o2.getCount() - o1.getCount();
            }
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for (CounterOfTrainings counterOfTraining : counterOfTrainings) {
            result.put(counterOfTraining.getCoach().getSurname(), counterOfTraining.getCount());
        }

        return result;
    }
}
