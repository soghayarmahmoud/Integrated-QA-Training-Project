#  Integrated QA Training Project

![Version](https://img.shields.io/badge/version-1.0-blue)
![Status](https://img.shields.io/badge/status-active-brightgreen)
![Platforms](https://img.shields.io/badge/platform-Web%20%7C%20API%20%7C%20DB-lightgrey)

> A comprehensive QA training project covering Web UI, REST API, and Database testing.

## 🎯 Objectives
- Validate end-to-end e-commerce flow on **SauceDemo** (UI)
- Perform CRUD operations testing on **Fake Store API**
- Verify complex SQL JOIN queries on **Sakila Database**

##  Platforms Under Test

| Platform | Tool | Environment |
|----------|------|-------------|
| Web UI | Selenium / Cypress | SauceDemo (https://www.saucedemo.com) |
| API | Postman / REST Client | Fake Store API (https://fakestoreapi.com) |
| Database | MySQL / PostgreSQL | Sakila Sample Database |

##  Project Structure

```bash
├── docs/               # Test plans & strategy
├── test-cases/         # Test cases per platform
├── test-reports/       # Report templates
├── scripts/            # Postman collections & SQL scripts
└── assets/             # Supporting files
