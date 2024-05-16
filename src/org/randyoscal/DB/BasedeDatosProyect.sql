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
    primary key PK_idTipoProducto(idTipoProducto)
);

-- ---------------------------------- Procedimiento alamacenamientos --------------------
-- ---------------------------------- Clientes ------------------------------------------
-- ---------------------------------- Agregar Clientes ----------------------------------
Delimiter $$
	create procedure sp_AgregarClientes(in codigoCliente int, NITcliente varchar(10), in nombreCliente varchar(50), in apellidoCliente varchar(50),
    in direccionCliente varchar(150), in telefonoCliente varchar(8), in correoCliente varchar(45))
		begin
			insert into Clientes (codigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente) values
            (codigoCliente, NITcliente, nombreCliente, apellidoCliente, direccionCliente,
            telefonoCliente, correoCliente);
		End $$
Delimiter ;

call sp_AgregarClientes (1, '26851', 'Harol', 'Luna', 'fdsgdsfg', '78945612', 'fghtstrew');
call sp_AgregarClientes (8, '1598', 'Oliver', 'Doinis', 'sdfgds', '48250783', 'sdfgdssuyrtfg');


Delimiter $$
	create procedure sp_ListarClientes()
		Begin
			select
            C.codigoCliente,
            C.NITcliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C;
		End $$
Delimiter ;

call sp_ListarClientes();

Delimiter $$
	create procedure sp_BuscarClientes(in codigoC int)
		Begin
			select
            C.codigoCliente,
            C.NITcliente,
            C.nombreCliente,
            C.apellidoCliente,
            C.direccionCliente,
            C.telefonoCliente,
            C.correoCliente
            from Clientes C
            where codigoCliente = codigoC;
		End $$
Delimiter ;

call sp_BuscarClientes (1);


Delimiter $$
	create procedure sp_EliminarClientes(in codigoC int)
		Begin
			Delete from Clientes
				where codigoCliente = codigoC;
		End $$
Delimiter ;

call sp_EliminarClientes(1);
call sp_ListarClientes();

Delimiter $$
	create procedure sp_EditarClientes (in codigoC int, in NitC varchar(10), in nombreC varchar(50), in apellidoC varchar(50),
			in direccionC varchar(150), in telefonoC varchar(8), in correoC varchar(45))
				Begin
					update Clientes C
						set
					C.codigoCliente = codigoC,
					C.NITcliente = NitC,
					C.nombreCliente = nombreC,
					C.apellidoCliente = apellidoC,
					C.direccionCliente = direccionC,
					C.telefonoCliente = telefonoC,
					C.correoCliente = correoC
                    where C.codigoCliente  = codigoC;
				end $$
Delimiter ;







-- ---------------------------------- Procedimiento alamacenamientos --------------------

-- ---------------------------------------- Crud de proveedores --------------------------------------

Delimiter $$
	create procedure sp_AgregarProveedores(in codigoProveedor int, NITproveedor varchar(10), in nombreProveedor varchar(50), in apellidoProveedor varchar(50),
    in direccionProveedor varchar(150), in razonSocial varchar(8), in contactoPrincipal varchar(45), in paginaWeb varchar(50))
		begin
			insert into Proveedores (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor,
            razonSocial, contactoPrincipal, paginaWeb) values
            (codigoProveedor, NITproveedor, nombreProveedor, apellidoProveedor, direccionProveedor,
            razonSocial, contactoPrincipal, paginaWeb);
		End $$
Delimiter ;

call sp_AgregarProveedores (1, '268516', 'Harol', 'Luna', 'fdsgdsfg', '7894', 'fghysstrew', 'vgbyjj');
call sp_AgregarProveedores (9, '15987', 'Oliver', 'Doinis', 'sdfdf', '482583', '12345678', 'uvyyuv');


Delimiter $$
	create procedure sp_ListarProveedores()
		Begin
			select
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P;
		End $$
Delimiter ;

call sp_ListarProveedores();

Delimiter $$
	create procedure sp_BuscarProveedores(in codigoP int)
		Begin
			select
            P.codigoProveedor,
            P.NITproveedor,
            P.nombreProveedor,
            P.apellidoProveedor,
            P.direccionProveedor,
            P.razonSocial,
            P.contactoPrincipal,
            P.paginaWeb
            from Proveedores P
            where codigoProveedor = codigoP;
		End $$
Delimiter ;

call sp_BuscarProveedores (1);


Delimiter $$
	create procedure sp_EliminarProveedores(in codigoP int)
		Begin
			Delete from Proveedores
				where codigoProveedor = codigoP;
		End $$
Delimiter ;

call sp_EliminarProveedores(1);
call sp_ListarProveedores();

Delimiter $$
	create procedure sp_EditarProveedores (in codigoP int, in NitP varchar(10), in nombreP varchar(50), in apellidoP varchar(50),
			in direccionP varchar(150), in razonS varchar(8), in contactoP varchar(45), in paginaW varchar(50))
				Begin
					update Proveedores P
						set
					P.codigoProveedor = codigoP,
					P.NITproveedor = NitP,
					P.nombreProveedor = nombreP,
					P.apellidoProveedor = apellidoP,
					P.direccionProveedor = direccionP,
					P.razonSocial = razonS,
					P.contactoPrincipal = contactoP,
                    P.paginaWeb = paginaW
                    where P.codigoProveedor  = codigoP;
				end $$
Delimiter ;

-- ---------------------------------------------- Procedimiento alamacenamientos ----------------------------------------
-- ------------------------------------------------------ Compras ---------------------------------------------

Delimiter $$
	create procedure sp_AgregarCompras(in numeroDocumento int, in fechaDocumento date, in descripcion varchar(60), in totalDocumento decimal(10,2))
		begin
			insert into Compras (numeroDocumento, fechaDocumento, descripcion, totalDocumento) values
            (numeroDocumento, fechaDocumento, descripcion, totalDocumento);
		End $$
Delimiter ;

call sp_AgregarCompras(10, '2024-04-30','Compra mayoritaria', 120.50);

Delimiter $$
	create procedure sp_ListarCompras()
		Begin
			select
			Co.numeroDocumento,
            Co.fechaDocumento,
            Co.descripcion,
            Co.totalDocumento
            from Compras Co;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_BuscarCompras(in id int)
		Begin
			select
			Co.numeroDocumento,
            Co.fechaDocumento,
            Co.descripcion,
            Co.totalDocumento
            from Compras Co
            where numeroDocumento = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EliminarCompras(in id int)
		Begin
			Delete from Compras
				where numeroDocumento = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EditarCompras(in numeroDoc int, in fechaDoc date, in descr varchar(60), in totalDoc decimal(10, 2))
				Begin
					update Compras Co
						set
					Co.numeroDocumento = numeroDoc,
					Co.fechaDocumento = fechaDoc,
					Co.descripcion = descr,
					Co.totalDocumento = totalDoc
					where Co.numeroDocumento = numeroDoc;
				end $$
Delimiter ;


-- ---------------------------------------------- Procedimiento alamacenamientos ----------------------------------------
-- ------------------------------------------------------ CargoEmpleado --------------------------------------------



Delimiter $$
	create procedure sp_AgregarCargoEmpleado(in idCargoEmpleado int, in nombreCargo varchar(50), in descripcionCargo varchar(50))
		begin
			insert into CargoEmpleado (idCargoEmpleado, nombreCargo, descripcionCargo) values
            (idCargoEmpleado, nombreCargo, descripcionCargo);
		End $$
Delimiter ;

call sp_AgregarCargoEmpleado (8, 'Oliver', 'uvyyuv');
call sp_AgregarCargoEmpleado (9, 'Olver', 'uvyv');

Delimiter $$
	create procedure sp_ListarCargoEmpleado()
		Begin
			select
			Ca.idCargoEmpleado,
            Ca.nombreCargo,
            Ca.descripcionCargo
            from CargoEmpleado Ca;
		End $$
Delimiter ;

call sp_ListarCargoEmpleado();

Delimiter $$
	create procedure sp_BuscarCargoEmpleado(in id int)
		Begin
			select
			Ca.idCargoEmpleado,
            Ca.nombreCargo,
            Ca.descripcionCargo
            from CargoEmpleado Ca
            where numeroDocumento = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EliminarCargoEmpleado(in id int)
		Begin
			Delete from CargoEmpleado
				where idCargoEmpleado = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EditarCargoEmpleado(in idCargoEm int, in nomCargo varchar(50), in desCargo varchar(50))
				Begin
					update CargoEmpleado Ca
						set
                        Ca.idCargoEmpleado = idCargoEm,
                        Ca.nombreCargo = nomCargo,
                        Ca.descripcionCargo = desCargo
					where Ca.idCargoEmpleado = idCargoEm;
				end $$
Delimiter ;

-- ---------------------------------------------- Procedimiento alamacenamientos ----------------------------------------
-- ------------------------------------------------------ tipoProducto --------------------------------------------

Delimiter $$
	create procedure sp_AgregarTipoProducto(in idTipoProducto int, in descripcion varchar(50))
		begin
			insert into TipoProducto (idTipoProducto, descripcion) values
            (idTipoProducto, descripcion);
		End $$
Delimiter ;

call sp_AgregarTipoProducto(15,'Cereal de trigo');

Delimiter $$
	create procedure sp_ListarTipoProducto()
		Begin
			select
			Tp.idTipoProducto,
            Tp.descripcion
            from TipoProducto Tp;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_BuscarTipoProducto(in id int)
		Begin
			select
			Tp.idTipoProducto,
            Tp.descripcion
            from TipoProducto Tp
            where idTipoProducto = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EliminarTipoProducto(in id int)
		Begin
			Delete from TipoProducto
				where idTipoProducto = id;
		End $$
Delimiter ;


Delimiter $$
	create procedure sp_EditarTipoProducto(in id int, in descr varchar(50))
				Begin
					update TipoProducto Tp
						set
                        Tp.idTipoProducto = id,
                        Tp.descripcion = descr
					where idTipoProducto = id;
				end $$
Delimiter ;
			
set global time_zone = '-6:00';





