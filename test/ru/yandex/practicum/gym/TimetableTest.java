package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверка содержимого
        List<TrainingSession> test = List.of(singleTrainingSession);
        Assertions.assertEquals(test, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY));
        //Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(mondayChildTrainingSession, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).get(0));
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        List<TrainingSession> test = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        Assertions.assertEquals(thursdayAdultTrainingSession, test.get(1));
        Assertions.assertEquals(thursdayChildTrainingSession, test.get(0));
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        TrainingSession testAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        Assertions.assertEquals(testAt13, singleTrainingSession);
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        TrainingSession testAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0));

        Assertions.assertNull(testAt14);
    }

    @Test
    void testgetCountByCoaches() {
        Timetable timetable = new Timetable();

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);

        Coach coachVasiliev = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coachIvanov = new Coach("Иванов", "Иван", "Иванович");
        Coach coachPetrov = new Coach("Петров", "Петр", "Петрович");

        TrainingSession trainingSessionVasiliev1 =
                new TrainingSession(groupAdult, coachVasiliev, DayOfWeek.MONDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSessionIvanov1 =
                new TrainingSession(groupAdult, coachIvanov, DayOfWeek.TUESDAY, new TimeOfDay(11, 0));
        TrainingSession trainingSessionIvanov2 =
                new TrainingSession(groupChild, coachIvanov, DayOfWeek.TUESDAY, new TimeOfDay(18, 0));
        TrainingSession trainingSessionPetrov1 =
                new TrainingSession(groupChild, coachPetrov, DayOfWeek.SATURDAY, new TimeOfDay(13, 0));
        TrainingSession trainingSessionPetrov2 =
                new TrainingSession(groupChild, coachPetrov, DayOfWeek.SUNDAY, new TimeOfDay(14, 0));
        TrainingSession trainingSessionPetrov3 =
                new TrainingSession(groupAdult, coachPetrov, DayOfWeek.SUNDAY, new TimeOfDay(16, 0));

        timetable.addNewTrainingSession(trainingSessionVasiliev1);
        timetable.addNewTrainingSession(trainingSessionIvanov1);
        timetable.addNewTrainingSession(trainingSessionIvanov2);
        timetable.addNewTrainingSession(trainingSessionPetrov1);
        timetable.addNewTrainingSession(trainingSessionPetrov2);
        timetable.addNewTrainingSession(trainingSessionPetrov3);

        Map<String, Integer> result = timetable.getCountByCoaches();

        Assertions.assertEquals(3, result.size());

        //Проверка: количество тренировок каждого тренера
        Assertions.assertEquals(3, result.get("Петров"));
        Assertions.assertEquals(2, result.get("Иванов"));
        Assertions.assertEquals(1, result.get("Васильев"));

        //Проверка: У Петрова больше занятий чем у других занятий
        Assertions.assertEquals(3, result.get("Петров"));

        //Проверка: Первый идет тот, у кого больше проведенных занятий
        List<String> place = new ArrayList<>(result.keySet());
        String firstSurrName = place.getFirst();
        Assertions.assertEquals("Петров", firstSurrName);

        //Доп. проверка: все идут в нужном порядке по убыванию
        String middle = place.get(1);
        String last = place.getLast();

        Assertions.assertEquals("Иванов", middle);
        Assertions.assertEquals("Васильев", last);
    }
}
