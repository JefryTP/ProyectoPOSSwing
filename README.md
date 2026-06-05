# ProyectoPOS — Sistema de Punto de Venta para Bodega
Proyecto final de la materia de Programación Orientada a Objetos.

---

## Integrantes y ramas
| Integrante | Rama |
|---|---|
| Jefry | `programador/jefry` |
| Edilson | `programador/edilson` |
| Jair | `programador/jair` |
| Joffre | `programador/joffre` |
| Jaren | `programador/jaren` |
| Jim | `programador/jim` |
| Jefferson | `programador/jefferson` |

---

## Estructura de ramas
```
main        ← versión estable final (solo Jefry fusiona aquí)
develop     ← rama de integración (solo Jefry fusiona aquí)
programador/nombre  ← rama personal de cada integrante
```

---

## Configuración inicial (solo la primera vez)

### 1. Instalar Git
Descarga e instala Git desde: https://git-scm.com

### 2. Configurar tu identidad
Abre Git Bash y ejecuta:
```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tu@email.com"
```

### 3. Clonar el repositorio
```bash
git clone https://github.com/JefryTP/ProyectoPOSSwing.git
cd ProyectoPOSSwing
```

### 4. Ubicarte en TU rama
Cada integrante ejecuta solo la línea que le corresponde:
```bash
git checkout programador/edilson
git checkout programador/jair
git checkout programador/joffre
git checkout programador/jaren
git checkout programador/jim
git checkout programador/jefferson
```

### 5. Abrir el proyecto en NetBeans
File → Open Project → selecciona la carpeta del repositorio.

---

## Rutina diaria de trabajo

### Al inicio del día — actualizar tu rama
En NetBeans: **Git → Remote → Pull**

En la ventana que aparece marca solo:
- ✅ `develop -> origin/develop`

Esto trae los últimos cambios del proyecto a tu rama.

### Durante el día — guardar tu avance
Cada vez que termines algo concreto guarda tu progreso.

En NetBeans: **Git → Commit**
- Escribe un mensaje describiendo lo que hiciste
- Ejemplos de buenos mensajes:
  - `agrega pantalla de ventas`
  - `corrige error en calculo de total`
  - `conecta boton ingresar con la base de datos`
- Clic en **Commit**

### Al terminar — subir tu trabajo
En NetBeans: **Team → Remote → Push**

Confirma que sube a `programador/tu-nombre` y clic en **Finish**.

### Avisar a Jefry
Cuando termines una funcionalidad completa mándale mensaje a Jefry indicando que ya hiciste push y qué es lo que hiciste.

---

## Reglas del equipo
- ❌ Nunca trabajar en `main` ni en `develop`
- ❌ Nunca hacer merge sin avisar a Jefry
- ✅ Siempre hacer Pull al inicio del día
- ✅ Commits frecuentes con mensajes descriptivos en español
- ✅ Un commit por cada cosa que termines

---

## Flujo completo resumido
```
Inicio del día
      ↓
   Pull (traer cambios de develop)
      ↓
   Trabajar en NetBeans
      ↓
   Commit (guardar avance con mensaje)
      ↓
   Push (subir a tu rama)
      ↓
   Avisar a Jefry
      ↓
   Jefry revisa y fusiona a develop
```

---

## Contacto
Cualquier duda o conflicto en el código contactar a Jefry antes de hacer cualquier merge.
