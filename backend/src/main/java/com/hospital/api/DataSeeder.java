package com.hospital.api;

import com.hospital.api.model.AppointmentEntity;
import com.hospital.api.model.DoctorEntity;
import com.hospital.api.model.PatientEntity;
import com.hospital.api.repository.AppointmentRepository;
import com.hospital.api.repository.DoctorRepository;
import com.hospital.api.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedDatabase(PatientRepository patients, DoctorRepository doctors,
            AppointmentRepository appointments) {
        return args -> {
            boolean hasOldData = patients.findAll().stream().anyMatch(p -> p.getName().equals("Amara Kapoor"));
            if (patients.count() == 0 || hasOldData) {
                appointments.deleteAll();
                patients.deleteAll();
                doctors.deleteAll();

                patients.save(patient(100, "Sarah Jenkins", 34, "Cardiology", "Hypertension", "ADMITTED", "Dr. Marcus Sterling",
                        "2024-05-10"));
                patients.save(patient(101, "David Miller", 58, "ICU", "Cardiac Arrest", "CRITICAL", "Dr. Anya Chen",
                        "2024-05-18"));
                patients.save(patient(102, "Elena Rostova", 27, "Orthopedics", "Fracture - Tibia", "SURGERY",
                        "Dr. Evelyn Vance", "2024-05-17"));
                patients.save(patient(103, "Liam Gallagher", 45, "Neurology", "Chronic Migraine", "OBSERVATION",
                        "Dr. Kenji Tanaka", "2024-05-19"));
                patients.save(patient(104, "Chloe Dupont", 62, "General", "Diabetes - T2", "DISCHARGED", "Dr. Anya Chen",
                        "2024-05-05"));

                doctors.save(doctor(200, "Dr. Marcus Sterling", "Cardiology", "+91-9823001122", "m.sterling@medicore.in", 12,
                        "Mon-Fri"));
                doctors.save(doctor(201, "Dr. Anya Chen", "ICU / CCU", "+91-9823002233", "a.chen@medicore.in", 8,
                        "24/7 On-call"));
                doctors.save(doctor(202, "Dr. Evelyn Vance", "Orthopedics", "+91-9823003344",
                        "e.vance@medicore.in", 10, "Mon-Sat"));
                doctors.save(doctor(203, "Dr. Kenji Tanaka", "Neurology", "+91-9823004455", "k.tanaka@medicore.in", 9,
                        "Tue-Sat"));

                appointments.save(appointment(300, "Sophia Martinez", "Dr. Marcus Sterling", "Cardiology", "2024-05-21", "09:00",
                        "SCHEDULED"));
                appointments.save(appointment(301, "Noah Campbell", "Dr. Evelyn Vance", "Ophthalmology", "2024-05-21",
                        "10:30", "IN_PROGRESS"));
                appointments.save(appointment(302, "Oliver Bennett", "Dr. Anya Chen", "Orthopedics", "2024-05-21", "12:00",
                        "SCHEDULED"));
                appointments.save(
                        appointment(303, "Lucas Silva", "Dr. Marcus Sterling", "General", "2024-05-21", "14:15", "SCHEDULED"));
                appointments.save(appointment(304, "Emma Watson", "Dr. Kenji Tanaka", "Neurology", "2024-05-21", "16:00",
                        "SCHEDULED"));
            }
        };
    }

    private PatientEntity patient(int id, String name, int age, String ward, String diagnosis, String status,
            String doctor, String admissionDate) {
        PatientEntity patient = new PatientEntity();
        patient.setId(id);
        patient.setName(name);
        patient.setAge(age);
        patient.setWard(ward);
        patient.setDiagnosis(diagnosis);
        patient.setStatus(status);
        patient.setDoctor(doctor);
        patient.setAdmissionDate(admissionDate);
        return patient;
    }

    private DoctorEntity doctor(int id, String name, String specialization, String phone, String email,
            int patientsCount, String availability) {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setId(id);
        doctor.setName(name);
        doctor.setSpecialization(specialization);
        doctor.setPhone(phone);
        doctor.setEmail(email);
        doctor.setPatientsCount(patientsCount);
        doctor.setAvailability(availability);
        return doctor;
    }

    private AppointmentEntity appointment(int id, String patientName, String doctorName, String department, String date,
            String time, String status) {
        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setId(id);
        appointment.setPatientName(patientName);
        appointment.setDoctorName(doctorName);
        appointment.setDepartment(department);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setStatus(status);
        return appointment;
    }
}
