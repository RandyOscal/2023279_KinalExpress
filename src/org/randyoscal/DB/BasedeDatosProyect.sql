 drop database DBKinalExpress;
create database DBKinalExpress;

use DBKinalExpress;

create table Clientes(
	codigoCliente int not null,
	NITcliente varchar(10) not null,
	nombreCliente varchar(50) not null,
	apellidoCliente varchar(50) not null,
	direccionCliente varchar(150),
	telefonoCliente varchar(8),
	correoCliente varchar(45),
    primary key PK_codigoCliente(codigoCliente)
);

create table Proveedores(
	codigoProveedor int not null,
    NITproveedor varchar(10)not null,
    nombreProveedor varchar(60),
    apellidoProveedor varchar(60),
    direccionProveedor varchar(150),
    razonSocial varchar(60),
    contactoPrincipal varchar(100),
    paginaWeb varchar(50),
	primary key PK_codigoProveedor(codigoProveedor)
);

create table Compras(
	numeroDocumento int not null,
    fechaDocumento date,
    descripcion varchar(60),
    totalDocumento decimal(10,2),
    primary key PK_numeroDocumento(numeroDocumento)
);

create table CargoEmpleado(
	idCargoEmpleado int not null,
    nombreCargo varchar(50),
    descripcionCargo varchar(50),
    primary key PK_idCargoEmpleado(idCargoEmpleado)
);

create table TipoProducto(
	idTipoProducto int not null,
    descripcion varchar(50),
    primary key PK_idTipoProducto (idTipoProducto)
);

create table Productos(
	codigoProducto varchar(15),
	descripcionProducto varchar(15),
	precioUnitario decimal(10,2),
	precioDocena decimal(10,2),
	precioMayor decimal(10,2),
	imagenProducto varchar(45),
	existencia int,
	idTipoProducto int,
	codigoProveedor int,
	primary key  PK_codigoProducto (codigoProducto),
    constraint FK_Productos_TipoProducto foreign key (idTipoProducto) references TipoProducto(idTipoProducto),
    constraint FK_Productos_Proveedores foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

create table Empleados(
codigoEmpleados int not null,
nombreEmpleado varchar(50),
apellidoEmpleado varchar(50),
sueldo decimal(10,2),
direccion varchar(150),
turno varchar(15),
idCargoEmpleado int,
primary key PK_codigoEmpleados (codigoEmpleados),
constraint FK_Empleados_CargoEmpleado foreign key (idCargoEmpleado) references CargoEmpleado(idCargoEmpleado)
);


create table Factura(
numeroFactura int not null,
estado varchar(50),
totalFactura decimal(10,2),
fechaFactura varchar(45),
codigoCliente int,
codigoEmpleados int,
primary key PK_numeroFactura (numeroFactura),
constraint FK_Factura_Clientes foreign key (codigoCliente) references Clientes(codigoCliente),
constraint FK_Factura_Empleados foreign key (codigoEmpleados) references Empleados(codigoEmpleados)
);

create table telefonoProveedor(
codigoTelefonoProveedor int not null,
numeroPrincipal varchar(8),
numeroSecundario varchar(8),
observaciones varchar(45),
codigoProveedor int,
primary key PK_codigoTelefonoProveedor (codigoTelefonoProveedor),
constraint FK_telefonoProveedor_Proveedores foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

create table emailProveedor(
codigoEmailProveedor int not null,
emailProveedor varchar(50),
descripcion varchar(100),
codigoProveedor int,
primary key PK_codigoEmailProveedor (codigoEmailProveedor),
constraint FK_emailProveedor_Proveedores foreign key (codigoProveedor) references Proveedores(codigoProveedor)
);

create table detalleCompra(
codigoDetalleCompra int not null,
costoUnitario decimal (10.2),
cantidad int,
codigoProducto varchar(15),
numeroDocumento int,
primary key PK_codigoDetalleCompra (codigoDetalleCompra),
constraint FK_detalleCompra_Productos foreign key (codigoProducto) references Productos(codigoProducto),
constraint FK_detalleCompra_Compras foreign key (numeroDocumento) references Compras(numeroDocumento)
);

create table detalleFactura (
codigoDetalleFactura int not null,
precioUnitario decimal(10,2),
cantidad int,
numeroFactura int,
codigoProducto varchar(15),
primary key PK_codigoDetalleFactura (codigoDetalleFactura),
constraint FK_detalleFactura_Factura foreign key (numeroFactura) references Factura(numeroFactura),
constraint FK_detalleFactura_Productos foreign key (codigoProducto) references Productos(codigoProducto)
);


-- /*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*//--------------------------------------------------------------------------------Clientes
-- Crear procedimiento para agregar un cliente
DELIMITER $$
CREATE PROCEDURE sp_AgregarCliente(
    IN p_codigoCliente INT,
    IN p_NITcliente VARCHAR(10),
    IN p_nombreCliente VARCHAR(50),
    IN p_apellidoCliente VARCHAR(50),
    IN p_direccionCliente VARCHAR(150),
    IN p_telefonoCliente VARCHAR(8),
    IN p_correoCliente VARCHAR(45)
)
BEGIN
    INSERT INTO Clientes (codigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente)
    VALUES (p_codigoCliente, p_NITcliente, p_nombreCliente, p_apellidoCliente, p_direccionCliente, p_telefonoCliente, p_correoCliente);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los clientes
DELIMITER $$
CREATE PROCEDURE sp_ListarClientes()
BEGIN
    SELECT codigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente
    FROM Clientes;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un cliente por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarClientePorCodigo(IN p_codigo INT)
BEGIN
    SELECT codigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente, telefonoCliente, correoCliente
    FROM Clientes
    WHERE codigoCliente = p_codigo;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un cliente por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarClientePorCodigo(IN p_codigo INT)
BEGIN
    DELETE FROM Clientes
    WHERE codigoCliente = p_codigo;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un cliente
DELIMITER $$
CREATE PROCEDURE sp_EditarCliente(
    IN p_codigo INT,
    IN p_NITcliente VARCHAR(10),
    IN p_nombreCliente VARCHAR(50),
    IN p_apellidoCliente VARCHAR(50),
    IN p_direccionCliente VARCHAR(150),
    IN p_telefonoCliente VARCHAR(8),
    IN p_correoCliente VARCHAR(45)
)
BEGIN
    UPDATE Clientes
    SET
        NITcliente = p_NITcliente,
        nombreCliente = p_nombreCliente,
        apellidoCliente = p_apellidoCliente,
        direccionCliente = p_direccionCliente,
        telefonoCliente = p_telefonoCliente,
        correoCliente = p_correoCliente
    WHERE codigoCliente = p_codigo;
END $$
DELIMITER ;




-.-.-.-..-.-.-.

-- Crear procedimiento para agregar un proveedor
DELIMITER $$
CREATE PROCEDURE sp_AgregarProveedor(
    IN p_codigoProveedor INT,
    IN p_NITproveedor VARCHAR(10),
    IN p_nombreProveedor VARCHAR(60),
    IN p_apellidoProveedor VARCHAR(60),
    IN p_direccionProveedor VARCHAR(150),
    IN p_razonSocial VARCHAR(60),
    IN p_contactoPrincipal VARCHAR(100),
    IN p_paginaWeb VARCHAR(50)
)
BEGIN
    INSERT INTO Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb)
    VALUES (p_codigoProveedor, p_NITproveedor, p_nombreProveedor, p_apellidoProveedor, p_direccionProveedor, p_razonSocial, p_contactoPrincipal, p_paginaWeb);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los proveedores
DELIMITER $$
CREATE PROCEDURE sp_ListarProveedores()
BEGIN
    SELECT codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb
    FROM Proveedores;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarProveedorPorCodigo(IN p_codigo INT)
BEGIN
    SELECT codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor, razonSocial, contactoPrincipal, paginaWeb
    FROM Proveedores
    WHERE codigoProveedor = p_codigo;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarProveedorPorCodigo(IN p_codigo INT)
BEGIN
    DELETE FROM Proveedores
    WHERE codigoProveedor = p_codigo;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un proveedor
DELIMITER $$
CREATE PROCEDURE sp_EditarProveedor(
    IN p_codigo INT,
    IN p_NITproveedor VARCHAR(10),
    IN p_nombreProveedor VARCHAR(60),
    IN p_apellidoProveedor VARCHAR(60),
    IN p_direccionProveedor VARCHAR(150),
    IN p_razonSocial VARCHAR(60),
    IN p_contactoPrincipal VARCHAR(100),
    IN p_paginaWeb VARCHAR(50)
)
BEGIN
    UPDATE Proveedores
    SET
        NITproveedor = p_NITproveedor,
        nombreProveedor = p_nombreProveedor,
        apellidoProveedor = p_apellidoProveedor,
        direccionProveedor = p_direccionProveedor,
        razonSocial = p_razonSocial,
        contactoPrincipal = p_contactoPrincipal,
        paginaWeb = p_paginaWeb
    WHERE codigoProveedor = p_codigo;
END $$
DELIMITER ;

 -- *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*


-- Crear procedimiento para agregar una compra
DELIMITER $$
CREATE PROCEDURE sp_AgregarCompra(
    IN p_numeroDocumento INT,
    IN p_fechaDocumento DATE,
    IN p_descripcion VARCHAR(60),
    IN p_totalDocumento DECIMAL(10,2)
)
BEGIN
    INSERT INTO Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento)
    VALUES (p_numeroDocumento, p_fechaDocumento, p_descripcion, p_totalDocumento);
END $$
DELIMITER ;

-- Crear procedimiento para listar todas las compras
DELIMITER $$
CREATE PROCEDURE sp_ListarCompras()
BEGIN
    SELECT numeroDocumento, fechaDocumento, descripcion, totalDocumento
    FROM Compras;
END $$
DELIMITER ;

-- Crear procedimiento para buscar una compra por su número de documento
DELIMITER $$
CREATE PROCEDURE sp_BuscarCompraPorNumeroDocumento(IN p_numeroDocumento INT)
BEGIN
    SELECT numeroDocumento, fechaDocumento, descripcion, totalDocumento
    FROM Compras
    WHERE numeroDocumento = p_numeroDocumento;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar una compra por su número de documento
DELIMITER $$
CREATE PROCEDURE sp_EliminarCompraPorNumeroDocumento(IN p_numeroDocumento INT)
BEGIN
    DELETE FROM Compras
    WHERE numeroDocumento = p_numeroDocumento;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de una compra
DELIMITER $$
CREATE PROCEDURE sp_EditarCompra(
    IN p_numeroDocumento INT,
    IN p_fechaDocumento DATE,
    IN p_descripcion VARCHAR(60),
    IN p_totalDocumento DECIMAL(10,2)
)
BEGIN
    UPDATE Compras
    SET
        fechaDocumento = p_fechaDocumento,
        descripcion = p_descripcion,
        totalDocumento = p_totalDocumento
    WHERE numeroDocumento = p_numeroDocumento;
END $$
DELIMITER ;

-- --*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*--

-- Crear procedimiento para agregar un cargo de empleado
DELIMITER $$
CREATE PROCEDURE sp_AgregarCargoEmpleado(
    IN p_idCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(50),
    IN p_descripcionCargo VARCHAR(50)
)
BEGIN
    INSERT INTO CargoEmpleado (idCargoEmpleado, nombreCargo, descripcionCargo)
    VALUES (p_idCargoEmpleado, p_nombreCargo, p_descripcionCargo);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los cargos de empleados
DELIMITER $$
CREATE PROCEDURE sp_ListarCargosEmpleado()
BEGIN
    SELECT idCargoEmpleado, nombreCargo, descripcionCargo
    FROM CargoEmpleado;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un cargo de empleado por su ID
DELIMITER $$
CREATE PROCEDURE sp_BuscarCargoEmpleadoPorID(IN p_idCargoEmpleado INT)
BEGIN
    SELECT idCargoEmpleado, nombreCargo, descripcionCargo
    FROM CargoEmpleado
    WHERE idCargoEmpleado = p_idCargoEmpleado;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un cargo de empleado por su ID
DELIMITER $$
CREATE PROCEDURE sp_EliminarCargoEmpleadoPorID(IN p_idCargoEmpleado INT)
BEGIN
    DELETE FROM CargoEmpleado
    WHERE idCargoEmpleado = p_idCargoEmpleado;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un cargo de empleado
DELIMITER $$
CREATE PROCEDURE sp_EditarCargoEmpleado(
    IN p_idCargoEmpleado INT,
    IN p_nombreCargo VARCHAR(50),
    IN p_descripcionCargo VARCHAR(50)
)
BEGIN
    UPDATE CargoEmpleado
    SET
        nombreCargo = p_nombreCargo,
        descripcionCargo = p_descripcionCargo
    WHERE idCargoEmpleado = p_idCargoEmpleado;
END $$
DELIMITER ;



-- Crear procedimiento para agregar un tipo de producto
DELIMITER $$
CREATE PROCEDURE sp_AgregarTipoProducto(
    IN p_idTipoProducto INT,
    IN p_descripcion VARCHAR(50)
)
BEGIN
    INSERT INTO TipoProducto (idTipoProducto, descripcion)
    VALUES (p_idTipoProducto, p_descripcion);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los tipos de producto
DELIMITER $$
CREATE PROCEDURE sp_ListarTiposProducto()
BEGIN
    SELECT idTipoProducto, descripcion
    FROM TipoProducto;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un tipo de producto por su ID
DELIMITER $$
CREATE PROCEDURE sp_BuscarTipoProductoPorID(IN p_idTipoProducto INT)
BEGIN
    SELECT idTipoProducto, descripcion
    FROM TipoProducto
    WHERE idTipoProducto = p_idTipoProducto;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un tipo de producto por su ID
DELIMITER $$
CREATE PROCEDURE sp_EliminarTipoProductoPorID(IN p_idTipoProducto INT)
BEGIN
    DELETE FROM TipoProducto
    WHERE idTipoProducto = p_idTipoProducto;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un tipo de producto
DELIMITER $$
CREATE PROCEDURE sp_EditarTipoProducto(
    IN p_idTipoProducto INT,
    IN p_descripcion VARCHAR(50)
)
BEGIN
    UPDATE TipoProducto
    SET descripcion = p_descripcion
    WHERE idTipoProducto = p_idTipoProducto;
END $$
DELIMITER ;

-- *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*

-- Crear procedimiento para agregar un producto
DELIMITER $$
CREATE PROCEDURE sp_AgregarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_imagenProducto VARCHAR(45),
    IN p_existencia INT,
    IN p_idTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO Productos (codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, idTipoProducto, codigoProveedor)
    VALUES (p_codigoProducto, p_descripcionProducto, p_precioUnitario, p_precioDocena, p_precioMayor, p_imagenProducto, p_existencia, p_idTipoProducto, p_codigoProveedor);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los productos
DELIMITER $$
CREATE PROCEDURE sp_ListarProductos()
BEGIN
    SELECT codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, idTipoProducto, codigoProveedor
    FROM Productos;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un producto por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarProductoPorCodigo(IN p_codigoProducto VARCHAR(15))
BEGIN
    SELECT codigoProducto, descripcionProducto, precioUnitario, precioDocena, precioMayor, imagenProducto, existencia, idTipoProducto, codigoProveedor
    FROM Productos
    WHERE codigoProducto = p_codigoProducto;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un producto por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarProductoPorCodigo(IN p_codigoProducto VARCHAR(15))
BEGIN
    DELETE FROM Productos
    WHERE codigoProducto = p_codigoProducto;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un producto
DELIMITER $$
CREATE PROCEDURE sp_EditarProducto(
    IN p_codigoProducto VARCHAR(15),
    IN p_descripcionProducto VARCHAR(15),
    IN p_precioUnitario DECIMAL(10,2),
    IN p_precioDocena DECIMAL(10,2),
    IN p_precioMayor DECIMAL(10,2),
    IN p_imagenProducto VARCHAR(45),
    IN p_existencia INT,
    IN p_idTipoProducto INT,
    IN p_codigoProveedor INT
)
BEGIN
    UPDATE Productos
    SET
        descripcionProducto = p_descripcionProducto,
        precioUnitario = p_precioUnitario,
        precioDocena = p_precioDocena,
        precioMayor = p_precioMayor,
        imagenProducto = p_imagenProducto,
        existencia = p_existencia,
        idTipoProducto = p_idTipoProducto,
        codigoProveedor = p_codigoProveedor
    WHERE codigoProducto = p_codigoProducto;
END $$
DELIMITER ;








-- Crear procedimiento para agregar un empleado
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nombreEmpleado VARCHAR(50),
    IN p_apellidoEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_idCargoEmpleado INT
)
BEGIN
    INSERT INTO Empleados (codigoEmpleados, nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, idCargoEmpleado)
    VALUES (p_codigoEmpleado, p_nombreEmpleado, p_apellidoEmpleado, p_sueldo, p_direccion, p_turno, p_idCargoEmpleado);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los empleados
DELIMITER $$
CREATE PROCEDURE sp_ListarEmpleados()
BEGIN
    SELECT codigoEmpleados, nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, idCargoEmpleado
    FROM Empleados;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un empleado por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmpleadoPorCodigo(IN p_codigoEmpleado INT)
BEGIN
    SELECT codigoEmpleados, nombreEmpleado, apellidoEmpleado, sueldo, direccion, turno, idCargoEmpleado
    FROM Empleados
    WHERE codigoEmpleados = p_codigoEmpleado;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un empleado por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmpleadoPorCodigo(IN p_codigoEmpleado INT)
BEGIN
    DELETE FROM Empleados
    WHERE codigoEmpleados = p_codigoEmpleado;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un empleado
DELIMITER $$
CREATE PROCEDURE sp_EditarEmpleado(
    IN p_codigoEmpleado INT,
    IN p_nombreEmpleado VARCHAR(50),
    IN p_apellidoEmpleado VARCHAR(50),
    IN p_sueldo DECIMAL(10,2),
    IN p_direccion VARCHAR(150),
    IN p_turno VARCHAR(15),
    IN p_idCargoEmpleado INT
)
BEGIN
    UPDATE Empleados
    SET
        nombreEmpleado = p_nombreEmpleado,
        apellidoEmpleado = p_apellidoEmpleado,
        sueldo = p_sueldo,
        direccion = p_direccion,
        turno = p_turno,
        idCargoEmpleado = p_idCargoEmpleado
    WHERE codigoEmpleados = p_codigoEmpleado;
END $$
DELIMITER ;



-- Crear procedimiento para agregar una factura
DELIMITER $$
CREATE PROCEDURE sp_AgregarFactura(
    IN p_numeroFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
    INSERT INTO Factura (numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleados)
    VALUES (p_numeroFactura, p_estado, p_totalFactura, p_fechaFactura, p_codigoCliente, p_codigoEmpleado);
END $$
DELIMITER ;

-- Crear procedimiento para listar todas las facturas
DELIMITER $$
CREATE PROCEDURE sp_ListarFacturas()
BEGIN
    SELECT numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleados
    FROM Factura;
END $$
DELIMITER ;

-- Crear procedimiento para buscar una factura por su número
DELIMITER $$
CREATE PROCEDURE sp_BuscarFacturaPorNumero(IN p_numeroFactura INT)
BEGIN
    SELECT numeroFactura, estado, totalFactura, fechaFactura, codigoCliente, codigoEmpleados
    FROM Factura
    WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar una factura por su número
DELIMITER $$
CREATE PROCEDURE sp_EliminarFacturaPorNumero(IN p_numeroFactura INT)
BEGIN
    DELETE FROM Factura
    WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de una factura
DELIMITER $$
CREATE PROCEDURE sp_EditarFactura(
    IN p_numeroFactura INT,
    IN p_estado VARCHAR(50),
    IN p_totalFactura DECIMAL(10,2),
    IN p_fechaFactura VARCHAR(45),
    IN p_codigoCliente INT,
    IN p_codigoEmpleado INT
)
BEGIN
    UPDATE Factura
    SET
        estado = p_estado,
        totalFactura = p_totalFactura,
        fechaFactura = p_fechaFactura,
        codigoCliente = p_codigoCliente,
        codigoEmpleados = p_codigoEmpleado
    WHERE numeroFactura = p_numeroFactura;
END $$
DELIMITER ;





-- Crear procedimiento para agregar un teléfono de proveedor
DELIMITER $$
CREATE PROCEDURE sp_AgregarTelefonoProveedor(
    IN p_codigoTelefonoProveedor INT,
    IN p_numeroPrincipal VARCHAR(8),
    IN p_numeroSecundario VARCHAR(8),
    IN p_observaciones VARCHAR(45),
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO telefonoProveedor (codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor)
    VALUES (p_codigoTelefonoProveedor, p_numeroPrincipal, p_numeroSecundario, p_observaciones, p_codigoProveedor);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los teléfonos de proveedor
DELIMITER $$
CREATE PROCEDURE sp_ListarTelefonosProveedor()
BEGIN
    SELECT codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor
    FROM telefonoProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un teléfono de proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarTelefonoProveedorPorCodigo(IN p_codigoTelefonoProveedor INT)
BEGIN
    SELECT codigoTelefonoProveedor, numeroPrincipal, numeroSecundario, observaciones, codigoProveedor
    FROM telefonoProveedor
    WHERE codigoTelefonoProveedor = p_codigoTelefonoProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un teléfono de proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarTelefonoProveedorPorCodigo(IN p_codigoTelefonoProveedor INT)
BEGIN
    DELETE FROM telefonoProveedor
    WHERE codigoTelefonoProveedor = p_codigoTelefonoProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un teléfono de proveedor
DELIMITER $$
CREATE PROCEDURE sp_EditarTelefonoProveedor(
    IN p_codigoTelefonoProveedor INT,
    IN p_numeroPrincipal VARCHAR(8),
    IN p_numeroSecundario VARCHAR(8),
    IN p_observaciones VARCHAR(45),
    IN p_codigoProveedor INT
)
BEGIN
    UPDATE telefonoProveedor
    SET
        numeroPrincipal = p_numeroPrincipal,
        numeroSecundario = p_numeroSecundario,
        observaciones = p_observaciones,
        codigoProveedor = p_codigoProveedor
    WHERE codigoTelefonoProveedor = p_codigoTelefonoProveedor;
END $$
DELIMITER ;


--------------------------------------------------------------------------------------------------------------

-- Crear procedimiento para agregar un correo electrónico de proveedor
DELIMITER $$
CREATE PROCEDURE sp_AgregarEmailProveedor(
    IN p_codigoEmailProveedor INT,
    IN p_emailProveedor VARCHAR(50),
    IN p_descripcion VARCHAR(100),
    IN p_codigoProveedor INT
)
BEGIN
    INSERT INTO emailProveedor (codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor)
    VALUES (p_codigoEmailProveedor, p_emailProveedor, p_descripcion, p_codigoProveedor);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los correos electrónicos de proveedor
DELIMITER $$
CREATE PROCEDURE sp_ListarEmailsProveedor()
BEGIN
    SELECT codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor
    FROM emailProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un correo electrónico de proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarEmailProveedorPorCodigo(IN p_codigoEmailProveedor INT)
BEGIN
    SELECT codigoEmailProveedor, emailProveedor, descripcion, codigoProveedor
    FROM emailProveedor
    WHERE codigoEmailProveedor = p_codigoEmailProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un correo electrónico de proveedor por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarEmailProveedorPorCodigo(IN p_codigoEmailProveedor INT)
BEGIN
    DELETE FROM emailProveedor
    WHERE codigoEmailProveedor = p_codigoEmailProveedor;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un correo electrónico de proveedor
DELIMITER $$
CREATE PROCEDURE sp_EditarEmailProveedor(
    IN p_codigoEmailProveedor INT,
    IN p_emailProveedor VARCHAR(50),
    IN p_descripcion VARCHAR(100),
    IN p_codigoProveedor INT
)
BEGIN
    UPDATE emailProveedor
    SET
        emailProveedor = p_emailProveedor,
        descripcion = p_descripcion,
        codigoProveedor = p_codigoProveedor
    WHERE codigoEmailProveedor = p_codigoEmailProveedor;
END $$
DELIMITER ;



------------------------------------------------------------------------------------------------------------------

-- Crear procedimiento para agregar un detalle de compra
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
    INSERT INTO detalleCompra (codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento)
    VALUES (p_codigoDetalleCompra, p_costoUnitario, p_cantidad, p_codigoProducto, p_numeroDocumento);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los detalles de compra
DELIMITER $$
CREATE PROCEDURE sp_ListarDetallesCompra()
BEGIN
    SELECT codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento
    FROM detalleCompra;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un detalle de compra por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleCompraPorCodigo(IN p_codigoDetalleCompra INT)
BEGIN
    SELECT codigoDetalleCompra, costoUnitario, cantidad, codigoProducto, numeroDocumento
    FROM detalleCompra
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un detalle de compra por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleCompraPorCodigo(IN p_codigoDetalleCompra INT)
BEGIN
    DELETE FROM detalleCompra
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un detalle de compra
DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleCompra(
    IN p_codigoDetalleCompra INT,
    IN p_costoUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_codigoProducto VARCHAR(15),
    IN p_numeroDocumento INT
)
BEGIN
    UPDATE detalleCompra
    SET
        costoUnitario = p_costoUnitario,
        cantidad = p_cantidad,
        codigoProducto = p_codigoProducto,
        numeroDocumento = p_numeroDocumento
    WHERE codigoDetalleCompra = p_codigoDetalleCompra;
END $$
DELIMITER ;
-------------------------------------------------------------------------------------------------------------------

-- Crear procedimiento para agregar un detalle de factura
DELIMITER $$
CREATE PROCEDURE sp_AgregarDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
    INSERT INTO detalleFactura (codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto)
    VALUES (p_codigoDetalleFactura, p_precioUnitario, p_cantidad, p_numeroFactura, p_codigoProducto);
END $$
DELIMITER ;

-- Crear procedimiento para listar todos los detalles de factura
DELIMITER $$
CREATE PROCEDURE sp_ListarDetallesFactura()
BEGIN
    SELECT codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto
    FROM detalleFactura;
END $$
DELIMITER ;

-- Crear procedimiento para buscar un detalle de factura por su código
DELIMITER $$
CREATE PROCEDURE sp_BuscarDetalleFacturaPorCodigo(IN p_codigoDetalleFactura INT)
BEGIN
    SELECT codigoDetalleFactura, precioUnitario, cantidad, numeroFactura, codigoProducto
    FROM detalleFactura
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;

-- Crear procedimiento para eliminar un detalle de factura por su código
DELIMITER $$
CREATE PROCEDURE sp_EliminarDetalleFacturaPorCodigo(IN p_codigoDetalleFactura INT)
BEGIN
    DELETE FROM detalleFactura
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;

-- Crear procedimiento para editar la información de un detalle de factura
DELIMITER $$
CREATE PROCEDURE sp_EditarDetalleFactura(
    IN p_codigoDetalleFactura INT,
    IN p_precioUnitario DECIMAL(10,2),
    IN p_cantidad INT,
    IN p_numeroFactura INT,
    IN p_codigoProducto VARCHAR(15)
)
BEGIN
    UPDATE detalleFactura
    SET
        precioUnitario = p_precioUnitario,
        cantidad = p_cantidad,
        numeroFactura = p_numeroFactura,
        codigoProducto = p_codigoProducto
    WHERE codigoDetalleFactura = p_codigoDetalleFactura;
END $$
DELIMITER ;


















-- Agregar un cliente
CALL sp_AgregarCliente(1, '1234567890', 'Juan', 'Pérez', 'Calle Principal 123', '12345678', 'juan@example.com');

-- Listar todos los clientes
CALL sp_ListarClientes();

-- Buscar un cliente por su código (por ejemplo, código 1)
CALL sp_BuscarClientePorCodigo(1);

-- Eliminar un cliente por su código (por ejemplo, código 2)
CALL sp_EliminarClientePorCodigo(2);

-- Editar la información de un cliente (por ejemplo, cambiar el correo del cliente con código 3)
CALL sp_EditarCliente(3, '9876543210', 'María', 'Gómez', 'Calle Secundaria 456', '87654321', 'maria@example.com');


-- Agregar un proveedor
CALL sp_AgregarProveedor(1, '1234567890', 'Proveedor', 'Uno', 'Calle Principal 123', 'Razón Uno', 'Contacto Uno', 'www.proveedoruno.com');

-- Listar todos los proveedores
CALL sp_ListarProveedores();

-- Buscar un proveedor por su código (por ejemplo, código 1)
CALL sp_BuscarProveedorPorCodigo(1);

-- Eliminar un proveedor por su código (por ejemplo, código 2)
CALL sp_EliminarProveedorPorCodigo(2);

-- Editar la información de un proveedor (por ejemplo, cambiar el contacto principal del proveedor con código 3)
CALL sp_EditarProveedor(3, '0987654321', 'Proveedor', 'Tres', 'Calle Secundaria 456', 'Razón Tres', 'Nuevo Contacto', 'www.proveedortres.com');




-- Agregar una compra
CALL sp_AgregarCompra(1, '2024-05-20', 'Compra de productos', 150.50);

-- Listar todas las compras
CALL sp_ListarCompras();

-- Buscar una compra por su número de documento (por ejemplo, número de documento 1)
CALL sp_BuscarCompraPorNumeroDocumento(1);

-- Eliminar una compra por su número de documento (por ejemplo, número de documento 2)
CALL sp_EliminarCompraPorNumeroDocumento(2);

-- Editar la información de una compra (por ejemplo, cambiar la descripción de la compra con número de documento 3)
CALL sp_EditarCompra(3, '2024-05-18', 'Compra de materiales', 200.75);




-- Agregar un cargo de empleado
CALL sp_AgregarCargoEmpleado(1, 'Gerente', 'Responsable de la dirección y gestión del personal');

-- Listar todos los cargos de empleados
CALL sp_ListarCargosEmpleado();

-- Buscar un cargo de empleado por su ID (por ejemplo, ID 1)
CALL sp_BuscarCargoEmpleadoPorID(1);

-- Eliminar un cargo de empleado por su ID (por ejemplo, ID 2)
CALL sp_EliminarCargoEmpleadoPorID(2);

-- Editar la información de un cargo de empleado (por ejemplo, cambiar el nombre del cargo con ID 3)
CALL sp_EditarCargoEmpleado(3, 'Supervisor', 'Encargado de supervisar el trabajo del personal');


-- Agregar un tipo de producto
CALL sp_AgregarTipoProducto(1, 'Electrónicos');

-- Listar todos los tipos de producto
CALL sp_ListarTiposProducto();

-- Buscar un tipo de producto por su ID (por ejemplo, ID 1)
CALL sp_BuscarTipoProductoPorID(1);

-- Eliminar un tipo de producto por su ID (por ejemplo, ID 2)
CALL sp_EliminarTipoProductoPorID(2);

-- Editar la información de un tipo de producto (por ejemplo, cambiar la descripción del tipo de producto con ID 3)
CALL sp_EditarTipoProducto(3, 'Hogar');



-- Agregar un producto
CALL sp_AgregarProducto('PROD001', 'Producto 1', 10.00, 100.00, 500.00, 'imagen1.jpg', 50, 1, 1);

-- Listar todos los productos
CALL sp_ListarProductos();

-- Buscar un producto por su código (por ejemplo, código 'PROD001')
CALL sp_BuscarProductoPorCodigo('PROD001');

-- Eliminar un producto por su código (por ejemplo, código 'PROD002')
CALL sp_EliminarProductoPorCodigo('PROD002');

-- Editar la información de un producto (por ejemplo, cambiar la descripción del producto con código 'PROD003')
CALL sp_EditarProducto('PROD003', 'Nuevo Producto', 15.00, 150.00, 700.00, 'imagen2.jpg', 30, 2, 2);



-- Agregar un empleado
CALL sp_AgregarEmpleado(1, 'Juan', 'Pérez', 1500.00, 'Calle Principal 123', 'Mañana', 1);

-- Listar todos los empleados
CALL sp_ListarEmpleados();

-- Buscar un empleado por su código (por ejemplo, código 1)
CALL sp_BuscarEmpleadoPorCodigo(1);

-- Eliminar un empleado por su código (por ejemplo, código 2)
CALL sp_EliminarEmpleadoPorCodigo(2);

-- Editar la información de un empleado (por ejemplo, cambiar el sueldo del empleado con código 3)
CALL sp_EditarEmpleado(3, 'María', 'Gómez', 1700.00, 'Calle Secundaria 456', 'Noche', 2);


-- Agregar una factura
CALL sp_AgregarFactura(1, 'Pagada', 150.50, '2024-05-20', 1, 1);

-- Listar todas las facturas
CALL sp_ListarFacturas();

-- Buscar una factura por su número (por ejemplo, número 1)
CALL sp_BuscarFacturaPorNumero(1);

-- Eliminar una factura por su número (por ejemplo, número 2)
CALL sp_EliminarFacturaPorNumero(2);

-- Editar la información de una factura (por ejemplo, cambiar el estado de la factura con número 3)
CALL sp_EditarFactura(3, 'Anulada', 200.75, '2024-05-18', 2, 2);


-- Agregar un teléfono de proveedor
CALL sp_AgregarTelefonoProveedor(1, '12345678', '98765432', 'Observaciones 1', 1);

-- Listar todos los teléfonos de proveedor
CALL sp_ListarTelefonosProveedor();

-- Buscar un teléfono de proveedor por su código (por ejemplo, código 1)
CALL sp_BuscarTelefonoProveedorPorCodigo(1);

-- Eliminar un teléfono de proveedor por su código (por ejemplo, código 2)
CALL sp_EliminarTelefonoProveedorPorCodigo(2);

-- Editar la información de un teléfono de proveedor (por ejemplo, cambiar el número principal del teléfono con código 3)
CALL sp_EditarTelefonoProveedor(3, '87654321', '12345678', 'Nuevas observaciones', 2);


-- Agregar un detalle de compra
CALL sp_AgregarDetalleCompra(1, 10.50, 5, 'PROD001', 1);

-- Listar todos los detalles de compra
CALL sp_ListarDetallesCompra();

-- Buscar un detalle de compra por su código (por ejemplo, código 1)
CALL sp_BuscarDetalleCompraPorCodigo(1);

-- Eliminar un detalle de compra por su código (por ejemplo, código 2)
CALL sp_EliminarDetalleCompraPorCodigo(2);

-- Editar la información de un detalle de compra (por ejemplo, cambiar la cantidad del detalle de compra con código 3)
CALL sp_EditarDetalleCompra(3, 8.25, 3, 'PROD002', 2);


-- Agregar un detalle de factura
CALL sp_AgregarDetalleFactura(1, 10.50, 5, 1, 'PROD001');

-- Listar todos los detalles de factura
CALL sp_ListarDetallesFactura();

-- Buscar un detalle de factura por su código (por ejemplo, código 1)
CALL sp_BuscarDetalleFacturaPorCodigo(1);

-- Eliminar un detalle de factura por su código (por ejemplo, código 2)
CALL sp_EliminarDetalleFacturaPorCodigo(2);

-- Editar la información de un detalle de factura (por ejemplo, cambiar la cantidad del detalle de factura con código 3)
CALL sp_EditarDetalleFactura(3, 8.25, 3, 2, 'PROD002');



-- Función para calcular precios y total de compra
DELIMITER $$

CREATE FUNCTION calcularPreciosCompra(p_costoUnitario DECIMAL(10,2), p_cantidad INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE precioUnitario DECIMAL(10,2);
    DECLARE precioDocena DECIMAL(10,2);
    DECLARE precioMayor DECIMAL(10,2);

    SET precioUnitario = p_costoUnitario * 1.4; -- 40% de ganancia
    SET precioDocena = p_costoUnitario * 1.35 * 12; -- 35% de ganancia por docena
    SET precioMayor = p_costoUnitario * 1.25 * p_cantidad; -- 25% de ganancia por cantidad

    RETURN precioUnitario;
END;


DELIMITER ;

-- Función para calcular el total de la factura
DELIMITER $$

CREATE FUNCTION calcularTotalFactura(p_numeroFactura INT)
RETURNS DECIMAL(10,2)
DETERMINISTIC
BEGIN
    DECLARE total DECIMAL(10,2);
    
    SELECT SUM(precioUnitario * cantidad)
    INTO total
    FROM detalleFactura
    WHERE numeroFactura = p_numeroFactura;

    RETURN total;
END;


DELIMITER ;

-- Trigger para actualizar precios y existencia en la tabla de Productos cuando se inserta un detalle de compra
DELIMITER $$

CREATE TRIGGER actualizarPreciosExistencia AFTER INSERT ON detalleCompra
FOR EACH ROW
BEGIN
    DECLARE precio DECIMAL(10,2);
    
    SET precio = calcularPreciosCompra(NEW.costoUnitario, NEW.cantidad);
    
    UPDATE Productos
    SET precioUnitario = precio,
        precioDocena = precio * 12,
        precioMayor = precio * NEW.cantidad,
        existencia = existencia + NEW.cantidad
    WHERE codigoProducto = NEW.codigoProducto;
END$$

DELIMITER ;

-- Trigger para actualizar el precio unitario en la tabla de detalleFactura cuando se inserta un detalle de factura
DELIMITER $$

CREATE TRIGGER actualizarPrecioDetalleFactura AFTER INSERT ON detalleFactura
FOR EACH ROW
BEGIN
    DECLARE precio DECIMAL(10,2);
    
    SELECT precioUnitario INTO precio
    FROM Productos
    WHERE codigoProducto = NEW.codigoProducto;
    
    UPDATE detalleFactura
    SET precioUnitario = precio
    WHERE codigoDetalleFactura = NEW.codigoDetalleFactura;
END$$

DELIMITER ;

-- Trigger para calcular el total de la factura en la tabla de Factura cuando se inserta un detalle de factura
DELIMITER $$

CREATE TRIGGER calcularTotalDocumento AFTER INSERT ON detalleFactura
FOR EACH ROW
BEGIN
    DECLARE total DECIMAL(10,2);
    
    SET total = calcularTotalFactura(NEW.numeroFactura);
    
    UPDATE Factura
    SET totalFactura = total
    WHERE numeroFactura = NEW.numeroFactura;
END$$

DELIMITER ;





set global time_zone = '-6:00';
