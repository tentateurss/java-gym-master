package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, TreeMap<TimeOfDay, TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, TrainingSession> dayTraining = timetable.get(dayOfWeek);
        if (dayTraining == null) {
            dayTraining = new TreeMap<>();
            timetable.put(dayOfWeek, dayTraining);
        }

        dayTraining.put(timeOfDay, trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> dayTraining = timetable.get(dayOfWeek);

        if (dayTraining == null || dayTraining.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(dayTraining.values());
    }

    public TrainingSession getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        TreeMap<TimeOfDay, TrainingSession> dayTraining = timetable.get(dayOfWeek);

        if (dayTraining == null) {
            return null;
        }

        return dayTraining.get(timeOfDay);
    }

    public Map<String, Integer> getCountByCoaches() {
        Map<Coach, Integer> coachCounter = new HashMap<>();

        for (TreeMap<TimeOfDay, TrainingSession> value : timetable.values()) {
            for (TrainingSession trainingSession : value.values()) {
                Coach coach = trainingSession.getCoach();
                coachCounter.put(coach, coachCounter.getOrDefault(coach, 0) + 1);
            }
        }

        List<CounterOfTrainings> counterOfTrainingsList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachCounter.entrySet()) {
            CounterOfTrainings counterOfTrainings = new CounterOfTrainings(entry.getKey(), entry.getValue());
            counterOfTrainingsList.add(counterOfTrainings);
        }

        Collections.sort(counterOfTrainingsList, new Comparator<CounterOfTrainings>() {
            @Override
            public int compare(CounterOfTrainings o1, CounterOfTrainings o2) {
                return o2.getCount() - o1.getCount();
            }
        });

        Map<String, Integer> result = new LinkedHashMap<>();
        for (CounterOfTrainings counterOfTrainings : counterOfTrainingsList) {
            result.put(counterOfTrainings.getCoach().getSurname(), counterOfTrainings.getCount());
        }

        return result;
    }
}
