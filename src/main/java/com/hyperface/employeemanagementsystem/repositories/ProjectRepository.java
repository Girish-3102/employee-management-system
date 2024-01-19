package com.hyperface.employeemanagementsystem.repositories;

import com.hyperface.employeemanagementsystem.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
}
