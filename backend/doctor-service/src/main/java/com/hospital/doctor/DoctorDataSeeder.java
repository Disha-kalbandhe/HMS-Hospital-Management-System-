package com.hospital.doctor;

import com.hospital.doctor.model.DoctorEntity;
import com.hospital.doctor.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DoctorDataSeeder {

    @Bean
    CommandLineRunner seedDatabase(DoctorRepository doctors) {
        return args -> {
            boolean hasNewData = doctors.findAll().stream().anyMatch(d -> d.getName().equals("Dr. Marcus Sterling"));
            if (doctors.count() == 0 || !hasNewData) {
                doctors.deleteAll();

                doctors.save(doctor(200, "Dr. Marcus Sterling", "Cardiology", "+91-9823001122", "m.sterling@medicore.in", 12,
                        "Mon-Fri"));
                doctors.save(doctor(201, "Dr. Anya Chen", "ICU / CCU", "+91-9823002233", "a.chen@medicore.in", 8,
                        "24/7 On-call"));
                doctors.save(doctor(202, "Dr. Evelyn Vance", "Orthopedics", "+91-9823003344",
                        "e.vance@medicore.in", 10, "Mon-Sat"));
                doctors.save(doctor(203, "Dr. Kenji Tanaka", "Neurology", "+91-9823004455", "k.tanaka@medicore.in", 9,
                        "Tue-Sat"));
            }
        };
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
}
