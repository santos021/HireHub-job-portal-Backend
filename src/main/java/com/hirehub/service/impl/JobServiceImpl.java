package com.hirehub.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hirehub.dto.JobRequest;
import com.hirehub.entity.Job;
import com.hirehub.entity.User;
import com.hirehub.repository.JobRepository;
import com.hirehub.service.JobService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
	
	private final JobRepository jobRepository;
	
	@Override
	public Job createJob(JobRequest request, User employer) {
		Job job = new Job();
		job.setTitle(request.getTitle());
		job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setLocation(request.getLocation());
        job.setSkills(request.getSkills());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setJobType(request.getJobType());
        job.setPostedBy(employer);
        
        return jobRepository.save(job);
	}

	@Override
	public Job updateJob(Long id, JobRequest request, User employer) {
		Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
		
		// Employers can update ONLY their own jobs
        if (!job.getPostedBy().getId().equals(employer.getId())) {
            throw new RuntimeException("You are not allowed to update this job");
        }

        job.setTitle(request.getTitle());
        job.setCompanyName(request.getCompanyName());
        job.setDescription(request.getDescription());
        job.setSalary(request.getSalary());
        job.setLocation(request.getLocation());
        job.setSkills(request.getSkills());
        job.setExperienceLevel(request.getExperienceLevel());
        job.setJobType(request.getJobType());

        return jobRepository.save(job);
	}

	@Override
	public void deleteJob(Long id, User employer) {
		Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
		
		if (!job.getPostedBy().getId().equals(employer.getId())) {
            throw new RuntimeException("You are not allowed to delete this job");
        }

        jobRepository.delete(job);
	}

	@Override
	public List<Job> getEmployerJobs(User employer) {
		return jobRepository.findByPostedBy(employer);
	}
}
