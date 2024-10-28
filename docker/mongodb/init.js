db.createUser(
    {
        user: "the-user",
        pwd: "the-password",
        roles: [
            {
                role: "readWrite",
                db: "pokedex"
            }
        ]
    }
);