const express = require("express");
const { Pool } = require("pg");
const app = express();
const port = 5000;

// Подключение к PostgreSQL
const pool = new Pool({
    user: "postgres",
    host: "localhost",
    database: "student",
    password: "123456",
    port: 5432,
});

// Middleware для обработки JSON
app.use(express.json());

// Получение всех курсов
app.get("/api/courses", async (req, res) => {
    try {
        const result = await pool.query("SELECT * FROM courses");
        res.json(result.rows);
    } catch (err) {
        console.error(err);
        res.status(500).send("server error");
    }
});

// Получение навыков для курса
app.get("/api/courses/:id/skills", async (req, res) => {
    const { id } = req.params;
    try {
        const result = await pool.query(
            `SELECT s.name 
     FROM skills s
     JOIN course_skills cs ON s.id = cs.skill_id
     WHERE cs.course_id = $1`,
            [id]
        );


        res.json(result.rows);
    } catch (err) {
        console.error(err);
        res.status(500).send("server error");
    }
});

// Запуск сервера
app.listen(port, () => {
    console.log(`Сервер запущен на http://localhost:${port}`);
});