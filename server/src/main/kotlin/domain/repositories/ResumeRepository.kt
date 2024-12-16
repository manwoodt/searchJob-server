package domain.repositories

import domain.models.Resume

interface ResumeRepository {
    fun getResumes(): List<Resume>

    fun getResumeById(id: Int): Resume?

    fun createTags(resume: Resume): List<String>
}