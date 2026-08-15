package com.vertice.app.data

import org.junit.Assert.*
import org.junit.Test

class FreelancerTest {

    @Test
    fun `Freelancer data class has all required fields`() {
        val freelancer = Freelancer(
            initials = "CM",
            name = "Carlos Mendes",
            role = "Pedreiro",
            area = "Construção Civil",
            match = 94,
            bg = 0xFF1E4ED8.toInt(),
            avail = "Disponível agora",
            rating = 4.9,
            jobs = 48,
            bio = "Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.",
            gender = "m",
            photoRes = null,
            photoUrl = null,
        )

        assertEquals("CM", freelancer.initials)
        assertEquals("Carlos Mendes", freelancer.name)
        assertEquals("Pedreiro", freelancer.role)
        assertEquals("Construção Civil", freelancer.area)
        assertEquals(94, freelancer.match)
        assertEquals(0xFF1E4ED8.toInt(), freelancer.bg)
        assertEquals("Disponível agora", freelancer.avail)
        assertEquals(4.9, freelancer.rating, 0.001)
        assertEquals(48, freelancer.jobs)
        assertEquals("Reformas residenciais, instalações hidráulicas e acabamento. 12 anos de experiência em SP.", freelancer.bio)
        assertEquals("m", freelancer.gender)
        assertNull(freelancer.photoRes)
        assertNull(freelancer.photoUrl)
    }

    @Test
    fun `Freelancer supports photoRes and photoUrl fields`() {
        val freelancerWithPhotoRes = Freelancer(
            initials = "AS",
            name = "Ana Silva",
            role = "Cabeleireira",
            area = "Beleza",
            match = 92,
            bg = 0xFFEC4899.toInt(),
            avail = "Disponível agora",
            rating = 4.8,
            jobs = 56,
            bio = "Especialista em cortes, coloração e tratamentos capilares.",
            gender = "f",
            photoRes = 0x7F080000, // Example drawable resource ID
            photoUrl = null,
        )

        assertNotNull(freelancerWithPhotoRes.photoRes)
        assertEquals(0x7F080000, freelancerWithPhotoRes.photoRes)
        assertNull(freelancerWithPhotoRes.photoUrl)

        val freelancerWithPhotoUrl = Freelancer(
            initials = "RO",
            name = "Roberto Oliveira",
            role = "Chef Particular",
            area = "Alimentação",
            match = 89,
            bg = 0xFFF97316.toInt(),
            avail = "Disponível amanhã",
            rating = 4.9,
            jobs = 42,
            bio = "Chef formado em gastronomia, cardápios personalizados.",
            gender = "m",
            photoRes = null,
            photoUrl = "https://i.pravatar.cc/150?u=roberto_oliveira",
        )

        assertNull(freelancerWithPhotoUrl.photoRes)
        assertNotNull(freelancerWithPhotoUrl.photoUrl)
        assertEquals("https://i.pravatar.cc/150?u=roberto_oliveira", freelancerWithPhotoUrl.photoUrl)

        val freelancerWithBoth = Freelancer(
            initials = "CF",
            name = "Camila Ferreira",
            role = "Consultora Financeira",
            area = "Consultoria",
            match = 95,
            bg = 0xFF059669.toInt(),
            avail = "Disponível esta semana",
            rating = 5.0,
            jobs = 38,
            bio = "Planejamento financeiro pessoal e para pequenos negócios.",
            gender = "f",
            photoRes = 0x7F080001,
            photoUrl = "https://i.pravatar.cc/150?u=camila_ferreira",
        )

        assertNotNull(freelancerWithBoth.photoRes)
        assertNotNull(freelancerWithBoth.photoUrl)
        assertEquals(0x7F080001, freelancerWithBoth.photoRes)
        assertEquals("https://i.pravatar.cc/150?u=camila_ferreira", freelancerWithBoth.photoUrl)
    }

    @Test
    fun `FREELANCERS list contains 12 freelancers`() {
        assertEquals(12, FREELANCERS.size)
    }

    @Test
    fun `FREELANCERS list contains original 6 freelancers`() {
        val names = FREELANCERS.map { it.name }.toSet()
        assertTrue(names.contains("Carlos Mendes"))
        assertTrue(names.contains("Luísa Fonseca"))
        assertTrue(names.contains("Beatriz Costa"))
        assertTrue(names.contains("Regina Oliveira"))
        assertTrue(names.contains("João Pereira"))
        assertTrue(names.contains("Marina Souza"))
    }

    @Test
    fun `FREELANCERS list contains 6 new diverse freelancers`() {
        val names = FREELANCERS.map { it.name }.toSet()
        assertTrue(names.contains("Ana Silva"))
        assertTrue(names.contains("Roberto Oliveira"))
        assertTrue(names.contains("Camila Ferreira"))
        assertTrue(names.contains("Diego Santos"))
        assertTrue(names.contains("André Pereira"))
        assertTrue(names.contains("Juliana Ribeiro"))
    }

    @Test
    fun `FREELANCERS covers all required areas`() {
        val areas = FREELANCERS.map { it.area }.toSet()
        assertTrue(areas.contains("Beleza"))
        assertTrue(areas.contains("Alimentação"))
        assertTrue(areas.contains("Consultoria"))
        assertTrue(areas.contains("Limpeza"))
        assertTrue(areas.contains("Construção"))
        assertTrue(areas.contains("Serviços"))
    }

    @Test
    fun `FREELANCERS has mix of genders for Violeta filter`() {
        val genders = FREELANCERS.map { it.gender }.toSet()
        assertTrue(genders.contains("m"))
        assertTrue(genders.contains("f"))

        val maleCount = FREELANCERS.count { it.gender == "m" }
        val femaleCount = FREELANCERS.count { it.gender == "f" }

        // At least 4 of each gender
        assertTrue("Should have at least 4 male freelancers", maleCount >= 4)
        assertTrue("Should have at least 4 female freelancers", femaleCount >= 4)
    }

    @Test
    fun `All freelancers have valid ratings`() {
        for (freelancer in FREELANCERS) {
            assertTrue("Rating should be between 0 and 5", freelancer.rating >= 0.0 && freelancer.rating <= 5.0)
            assertTrue("Jobs should be non-negative", freelancer.jobs >= 0)
            assertTrue("Match should be between 0 and 100", freelancer.match >= 0 && freelancer.match <= 100)
            assertTrue("Bio should not be empty", freelancer.bio.isNotEmpty())
            assertTrue("Initials should not be empty", freelancer.initials.isNotEmpty())
            assertTrue("Name should not be empty", freelancer.name.isNotEmpty())
            assertTrue("Role should not be empty", freelancer.role.isNotEmpty())
            assertTrue("Area should not be empty", freelancer.area.isNotEmpty())
            assertTrue("Avail should not be empty", freelancer.avail.isNotEmpty())
        }
    }

    @Test
    fun `New freelancers have photoUrl set`() {
        val newFreelancers = FREELANCERS.filter { it.name in listOf(
            "Ana Silva", "Roberto Oliveira", "Camila Ferreira",
            "Diego Santos", "André Pereira", "Juliana Ribeiro"
        ) }

        for (freelancer in newFreelancers) {
            assertNotNull("New freelancer ${freelancer.name} should have photoUrl", freelancer.photoUrl)
            assertTrue("photoUrl should be a valid URL", freelancer.photoUrl!!.startsWith("https://"))
        }
    }

    @Test
    fun `Freelancer equality and copy works`() {
        val original = Freelancer(
            initials = "CM",
            name = "Carlos Mendes",
            role = "Pedreiro",
            area = "Construção Civil",
            match = 94,
            bg = 0xFF1E4ED8.toInt(),
            avail = "Disponível agora",
            rating = 4.9,
            jobs = 48,
            bio = "Test bio",
            gender = "m",
            photoRes = null,
            photoUrl = "https://example.com/photo.jpg",
        )

        val copy = original.copy(rating = 5.0, jobs = 50)
        assertEquals(5.0, copy.rating, 0.001)
        assertEquals(50, copy.jobs)
        assertEquals("https://example.com/photo.jpg", copy.photoUrl)
        assertEquals(original.initials, copy.initials)
    }

    @Test
    fun `Freelancer toString contains all fields`() {
        val freelancer = Freelancer(
            initials = "CM",
            name = "Carlos Mendes",
            role = "Pedreiro",
            area = "Construção Civil",
            match = 94,
            bg = 0xFF1E4ED8.toInt(),
            avail = "Disponível agora",
            rating = 4.9,
            jobs = 48,
            bio = "Test bio",
            gender = "m",
            photoRes = 123,
            photoUrl = "https://example.com/photo.jpg",
        )

        val toString = freelancer.toString()
        assertTrue("toString should contain name", toString.contains("Carlos Mendes"))
        assertTrue("toString should contain role", toString.contains("Pedreiro"))
        assertTrue("toString should contain photoUrl", toString.contains("https://example.com/photo.jpg"))
        assertTrue("toString should contain photoRes", toString.contains("123"))
    }
}