import data.hardCodedInformation.HardCodedResumes
import domain.models.Resume
import domain.models.enums.Profession
import domain.models.enums.TypeOfEducation
import domain.repositories.ResumeRepository

class ResumeRepositoryImpl: ResumeRepository {

    private val hardCodedResumes = HardCodedResumes

    override fun getResumes(): List<Resume>{
        return hardCodedResumes.resumes
    }

    override fun getResumeById(id: Int): Resume? {
        return hardCodedResumes.resumes.getOrNull(id-1)
    }

    override fun createTags(resume: Resume): List<String> {
        val tags = mutableListOf<String>()

        if (resume.candidateInfo.profession == Profession.DEV) {
            tags.add("Developer")
        }
        if (resume.education.any { it.type == TypeOfEducation.HIGHER }) {
            tags.add("Higher Education")
        }
        if (resume.jobExperience.isNotEmpty()) {
            tags.add("Experienced")
        }

        println("Created tags for resume: $tags")

        return tags
    }

}