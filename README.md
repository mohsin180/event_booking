# 🎟️ Event Ticket Booking Platform

A **Spring Boot–based event management and ticket booking system** that allows event organizers to create, manage, and track events, while users can explore, purchase, and validate tickets seamlessly.

---

## 🚀 Features

- 👤 **User Management** — Register, log in, and manage user profiles.
- 🎪 **Event Creation** — Organizers can create and manage events with time, venue, and ticket details.
- 🎟️ **Ticket Booking** — Users can view available events and purchase tickets.
- 🔐 **Secure Authentication** — JWT-based authentication using keycloak.
- 🕒 **Auditing** — Automatic tracking of `createdAt` and `updatedAt` timestamps for all entities.
- 🧾 **Ticket Validation** — Integration with QR codes for ticket verification.
- 🗃️ **Database Support** — PostgreSQL for production, H2 for development/testing.

---

## 🧩 Tech Stack

| Layer | Technology |
|-------|-------------|
| **Backend** | Spring Boot (Java 17) |
| **Database** | PostgreSQL, H2 (runtime) |
| **Security** | KeyCloak |
| **ORM** | Spring Data JPA |
| **Mapping** | MapStruct (v1.6.3) |
| **Code Simplification** | Lombok (v1.18.36) |
| **Build Tool** | Maven |

---

git clone https://github.com/<your-username>/event-ticket-platform.git
cd event-ticket-platform
