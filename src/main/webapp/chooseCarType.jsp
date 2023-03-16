<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <title>Choose a type of your car</title>
    <link rel="stylesheet" href="style.css">
    <style>
        p {
            margin-bottom: 0.5rem;
        }

        .create-button {
            margin-top: 0.5rem;
        }

        input[type='radio']:after {
            width: 13px;
            height: 13px;
            border-radius: 50%;
            top: 0;
            left: 0;
            position: relative;
            background-color: hsl(120, 2%, 82%);
            content: '';
            display: inline-block;
            visibility: visible;
            border: 1px solid hsl(0, 0%, 100%);
        }

        input[type='radio']:checked:after {
            width: 13px;
            height: 13px;
            border-radius: 50%;
            top: 0;
            left: 0;
            position: relative;
            background-color: hsl(179, 86%, 41%);
            content: '';
            display: inline-block;
            visibility: visible;
            border: 1px solid hsl(0, 0%, 100%);
        }

        input {
            cursor: pointer;
        }

        .create-button {
            width: 80px;
            height: 40px;
            background-color: hsl(179, 100%, 28%);
            border-radius: 5px;
            font-size: 1rem;
            font-weight: 700;
            transition: background-color ease-in-out 0.2s;
        }

        .create-button:hover {
            background-color: hsl(179, 87%, 36%);
        }

        .create-button:active {
            background-color: hsl(179, 86%, 41%);
        }

        .list {
            margin-top: 2rem;
        }
    </style>
</head>

<body>
    <section>
        <h1 class="header">Choose type of your car:</h1>
        <form method="post" action="createCar.jsp">
            <div class="car-types">
                <p>
                    <input checked id="passenger-car" type="radio" name="type" value="CAR">
                    <label class="label" for="passenger-car">Passenger car</label>
                </p>
                <p>
                    <input id="truck" type="radio" name="type" value="TRUCK">
                    <label class="label" for="truck">Truck</label>
                </p>
            </div>
            <button class="create-button" type="submit">Create</button>
        </form>
        <div class="list">
            <a href="showAllCars.jsp">Show all cars</a>
        </div>
    </section>
</body>

</html>