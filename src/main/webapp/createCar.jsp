<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.shulha.model.Car" %>
<%@ page import="com.shulha.model.CarTypes" %>
<%@ page import="com.shulha.model.PassengerCar" %>
<%@ page import="com.shulha.model.Truck" %>
<%@ page import="com.shulha.service.CarService" %>
<%@ page import="com.shulha.model.Engine" %>
<%@ page import="com.shulha.model.EngineTypes" %>
<%@ page import="java.util.stream.Collectors" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.PrintWriter" %>
<html>

<head>
    <title>Creating a car</title>
    <link rel="stylesheet" href="style.css">
    <style>
        th, td {
            border:1px solid hsl(179, 100%, 28%);
        }

        table {
            margin: 10px;
        }

        td {
            padding: 0 10px;
        }
    </style>
</head>

<body>
    <section>
        <% String collect = request.getReader().lines().collect(Collectors.joining(System.lineSeparator())); %>
        <% String type = collect.split("=")[1]; %>
        <% CarTypes carType = Enum.valueOf(CarTypes.class, type); %>
        <% CarService carService = CarService.getInstance(); %>
        <% Car car = carService.createCar(carType); %>

        <table style="width:100%">
            <tr>
                <td>TYPE</td>
                <td>ID</td>
                <td>MANUFACTURER</td>
                <td>ENGINE ID</td>
                <td>ENGINE TYPE</td>
                <td>ENGINE POWER</td>
                <td>COLOR</td>
                <td>PRICE</td>
                <td>COUNT</td>
                <td>PASSENGER COUNT</td>
                <td>LOAD CAPACITY</td>
            </tr>
            <tr>
                <td><%= type %></td>
                <td><%= car.getId() %></td>
                <td><%= car.getManufacturer().toString() %></td>
                <td><%= car.getEngine().getId() %></td>
                <td><%= car.getEngine().getType() %></td>
                <td><%= car.getEngine().getPower() %></td>
                <td><%= car.getColor().toString() %></td>
                <td><%= car.getPrice() %></td>
                <td><%= car.getCount() %></td>
                <% if (carType == CarTypes.CAR) {%>
                <% PassengerCar passengerCar = (PassengerCar) car; %>
                <td><%= passengerCar.getPassengerCount() %></td>
                <td>-</td>
                <% } else { %>
                <% Truck truck = (Truck) car; %>
                <td>-</td>
                <td><%= truck.getLoadCapacity() %></td>
                <% } %>
            </tr>
        </table>
    </section>
</body>

</html>