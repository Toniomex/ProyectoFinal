/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/javascript.js to edit this template
 */
document.addEventListener('DOMContentLoaded', () => {
    // URL base de tu API de Spring Boot
    const API_BASE_URL = 'http://localhost:8080/api';

    // ID de la persona inquilina para esta demo (ej: Alice Smith del DemoRunner)
    // En una aplicación real, esto provendría de la autenticación del usuario.
    const CURRENT_USER_ID = 1; // Usaremos Alice Smith como el inquilino de prueba

    let currentUser = null; // Almacenará los datos del usuario actual

    // Referencias a elementos del DOM
    const mainContent = document.getElementById('main-content');
    const navButtons = document.querySelectorAll('.nav-button');
    const homePageActionButtons = document.querySelectorAll('.action-button');
    const currentUserNameDisplay = document.getElementById('current-user-name');
    const homeUserNameDisplay = document.getElementById('home-user-name');

    // Referencias para el modal de chat
    const chatModal = document.getElementById('chat-modal');
    const closeChatModalButton = document.getElementById('close-chat-modal');
    const chatMessagesArea = document.getElementById('chat-messages-area');
    const chatWarning = document.getElementById('chat-warning');
    const chatForm = document.getElementById('chat-form');
    const newMessageContentInput = document.getElementById('new-message-content');

    let currentChat = null; // Almacena el objeto del chat actual

    // --- Funciones de Utilidad ---

    /**
     * Muestra un mensaje de alerta en la parte superior del contenido principal.
     * @param {string} message El mensaje a mostrar.
     * @param {string} type El tipo de alerta ('green' para éxito, 'red' para error, 'yellow' para advertencia).
     */
    function showAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert-message ${type}`;
        alertDiv.textContent = message;
        mainContent.prepend(alertDiv); // Añadir al principio del contenido principal

        // Eliminar el mensaje después de 5 segundos
        setTimeout(() => {
            alertDiv.remove();
        }, 5000);
    }

    /**
     * Formatea una cadena de fecha a 'YYYY-MM-DD' para campos input[type="date"].
     * @param {string} dateString La cadena de fecha (ej. "2023-01-01T00:00:00").
     * @returns {string} La fecha formateada o una cadena vacía si es nula.
     */
    function formatDateForInput(dateString) {
        if (!dateString) return '';
        const date = new Date(dateString);
        return date.toISOString().split('T')[0];
    }

    /**
     * Formatea una fecha y hora para mostrarla en los mensajes del chat.
     * @param {string} datetimeString La cadena de fecha y hora (ej. "2025-07-29T12:04:09.899289733").
     * @returns {string} La fecha y hora formateada.
     */
    function formatDateTimeForDisplay(datetimeString) {
        if (!datetimeString) return '';
        const date = new Date(datetimeString);
        return date.toLocaleString(); // Formato localizado
    }

    // --- Gestión de Navegación ---

    /**
     * Oculta todas las secciones de página y muestra la sección activa.
     * @param {string} pageId El ID de la página a mostrar (ej. 'home-page').
     */
    function showPage(pageId) {
        document.querySelectorAll('.page-section').forEach(section => {
            section.classList.add('hidden');
            section.classList.remove('active');
        });
        document.getElementById(pageId).classList.remove('hidden');
        document.getElementById(pageId).classList.add('active');
    }

    /**
     * Maneja el clic en los botones de navegación.
     * @param {Event} event El evento de clic.
     */
    function handleNavClick(event) {
        const page = event.target.dataset.page;
        switch (page) {
            case 'home':
                showPage('home-page');
                break;
            case 'ubicaciones':
                renderUbicacionManagement();
                showPage('ubicaciones-page');
                break;
            case 'contratos':
                renderContratoManagement();
                showPage('contratos-page');
                break;
            case 'chats':
                // Abrir el modal de chat en lugar de navegar a una página
                openChatModal();
                break;
            default:
                showPage('home-page');
        }
    }

    // Añadir listeners a los botones de navegación y acción de la página de inicio
    navButtons.forEach(button => button.addEventListener('click', handleNavClick));
    homePageActionButtons.forEach(button => button.addEventListener('click', handleNavClick));

    // --- Funciones de Fetch a la API del Backend ---

    /**
     * Obtiene la información del usuario actual (inquilino).
     */
    async function fetchCurrentUser() {
        try {
            const response = await fetch(`${API_BASE_URL}/personas/${CURRENT_USER_ID}`);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            currentUser = await response.json();
            currentUserNameDisplay.textContent = currentUser.nombre;
            homeUserNameDisplay.textContent = currentUser.nombre;
        } catch (err) {
            console.error("Error al obtener la información del usuario actual:", err);
            currentUserNameDisplay.textContent = 'Error';
            homeUserNameDisplay.textContent = 'Error';
            showAlert("No se pudo cargar la información del usuario. Asegúrate de que el backend esté funcionando y el ID de usuario sea válido.", 'red');
        }
    }

    // --- Gestión de Ubicaciones ---

    /**
     * Renderiza el formulario de gestión de ubicación.
     */
    async function renderUbicacionManagement() {
        const ubicacionesPage = document.getElementById('ubicaciones-page');
        ubicacionesPage.innerHTML = '<h2 class="section-title">Cargando Mi Ubicación...</h2>'; // Mensaje de carga
        let ubicacion = null;

        try {
            // 1. Obtener el contrato del inquilino para obtener el ID de la ubicación
            const contratoResponse = await fetch(`${API_BASE_URL}/contratos/inquilino/${currentUser.idPersona}`);
            if (!contratoResponse.ok) {
                if (contratoResponse.status === 404) {
                    ubicacionesPage.innerHTML = '<h2 class="section-title">Gestionar Mi Ubicación</h2><p class="section-description">No se encontró un contrato asociado a este inquilino.</p>';
                    return;
                }
                throw new Error(`HTTP error! status: ${contratoResponse.status}`);
            }
            const contratoData = await contratoResponse.json();
            const ubicacionId = contratoData.ubicacion.idUbicacion; // Asumiendo que el contrato devuelve la ubicación anidada

            // 2. Obtener los detalles de la ubicación
            const ubicacionResponse = await fetch(`${API_BASE_URL}/ubicaciones/${ubicacionId}`);
            if (!ubicacionResponse.ok) {
                throw new Error(`HTTP error! status: ${ubicacionResponse.status}`);
            }
            ubicacion = await ubicacionResponse.json();

            // Construir el formulario de ubicación
            ubicacionesPage.innerHTML = `
                <h2 class="section-title">Gestionar Mi Ubicación</h2>
                <div id="ubicacion-message-area"></div>
                <form id="ubicacion-form" class="form-grid">
                    <div class="form-group">
                        <label for="calle">Calle:</label>
                        <input type="text" id="calle" value="${ubicacion.calle || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="numeroCalle">Número:</label>
                        <input type="text" id="numeroCalle" value="${ubicacion.numeroCalle || ''}">
                    </div>
                    <div class="form-group">
                        <label for="piso">Piso:</label>
                        <input type="text" id="piso" value="${ubicacion.piso || ''}">
                    </div>
                    <div class="form-group">
                        <label for="puerta">Puerta:</label>
                        <input type="text" id="puerta" value="${ubicacion.puerta || ''}">
                    </div>
                    <div class="form-group">
                        <label for="escalera">Escalera:</label>
                        <input type="text" id="escalera" value="${ubicacion.escalera || ''}">
                    </div>
                    <div class="form-group">
                        <label for="otro">Otro:</label>
                        <input type="text" id="otro" value="${ubicacion.otro || ''}">
                    </div>
                    <div class="form-group">
                        <label for="codigoPostal">Código Postal:</label>
                        <input type="text" id="codigoPostal" value="${ubicacion.codigoPostal || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="ciudad">Ciudad:</label>
                        <input type="text" id="ciudad" value="${ubicacion.ciudad || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="provincia">Provincia:</label>
                        <input type="text" id="provincia" value="${ubicacion.provincia || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="pais">País:</label>
                        <input type="text" id="pais" value="${ubicacion.pais || ''}" required>
                    </div>
                    <div class="form-actions full-width">
                        <button type="submit" class="submit-button">Actualizar Ubicación</button>
                    </div>
                </form>
            `;

            // Añadir listener al formulario para actualizar la ubicación
            const ubicacionForm = document.getElementById('ubicacion-form');
            if (ubicacionForm) {
                ubicacionForm.addEventListener('submit', async (e) => {
                    e.preventDefault();
                    const updatedUbicacion = {
                        idUbicacion: ubicacion.idUbicacion, // Asegurarse de enviar el ID
                        calle: document.getElementById('calle').value,
                        numeroCalle: document.getElementById('numeroCalle').value,
                        piso: document.getElementById('piso').value,
                        escalera: document.getElementById('escalera').value,
                        puerta: document.getElementById('puerta').value,
                        otro: document.getElementById('otro').value,
                        codigoPostal: document.getElementById('codigoPostal').value,
                        ciudad: document.getElementById('ciudad').value,
                        provincia: document.getElementById('provincia').value,
                        pais: document.getElementById('pais').value,
                    };

                    try {
                        const response = await fetch(`${API_BASE_URL}/ubicaciones/${ubicacion.idUbicacion}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(updatedUbicacion),
                        });

                        if (!response.ok) {
                            const errorData = await response.json();
                            throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                        }
                        showAlert("Ubicación actualizada exitosamente.", 'green');
                        renderUbicacionManagement(); // Recargar para mostrar los datos más recientes
                    } catch (err) {
                        console.error("Error al actualizar ubicación:", err);
                        showAlert(`Error al actualizar ubicación: ${err.message}`, 'red');
                    }
                });
            }

        } catch (err) {
            console.error("Error al cargar la ubicación:", err);
            ubicacionesPage.innerHTML = `<h2 class="section-title">Error al cargar Ubicación</h2><p class="section-description alert-message red">${err.message}</p>`;
        }
    }

    // --- Gestión de Contratos ---

    /**
     * Renderiza el formulario de gestión de contrato.
     */
    async function renderContratoManagement() {
        const contratosPage = document.getElementById('contratos-page');
        contratosPage.innerHTML = '<h2 class="section-title">Cargando Mi Contrato...</h2>'; // Mensaje de carga
        let contrato = null;

        try {
            const response = await fetch(`${API_BASE_URL}/contratos/inquilino/${currentUser.idPersona}`);
            if (!response.ok) {
                if (response.status === 404) {
                    contratosPage.innerHTML = '<h2 class="section-title">Gestionar Mi Contrato</h2><p class="section-description">No se encontró un contrato asociado a este inquilino.</p>';
                    return;
                }
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            contrato = await response.json();

            // Construir el formulario de contrato
            contratosPage.innerHTML = `
                <h2 class="section-title">Gestionar Mi Contrato</h2>
                <div id="contrato-message-area"></div>
                <form id="contrato-form" class="form-grid">
                    <div class="form-group">
                        <label for="precio">Precio:</label>
                        <input type="number" id="precio" step="0.01" value="${contrato.precio || ''}" required>
                    </div>
                    <div class="form-group">
                        <label for="duracion">Duración:</label>
                        <input type="text" id="duracion" value="${contrato.duracion || ''}">
                    </div>
                    <div class="form-group">
                        <label for="tipo">Tipo:</label>
                        <input type="text" id="tipo" value="${contrato.tipo || ''}">
                    </div>
                    <div class="form-group">
                        <label for="fechaInicio">Fecha Inicio:</label>
                        <input type="date" id="fechaInicio" value="${formatDateForInput(contrato.fechaInicio)}" required>
                    </div>
                    <div class="form-group">
                        <label for="fechaFinal">Fecha Final:</label>
                        <input type="date" id="fechaFinal" value="${formatDateForInput(contrato.fechaFinal)}" required>
                    </div>
                    <div class="form-group">
                        <label>NIF Arrendador:</label>
                        <p>${contrato.idArrendadorNIF || 'N/A'}</p>
                    </div>
                    <div class="form-group">
                        <label>Ubicación (ID):</label>
                        <p>${contrato.ubicacion ? contrato.ubicacion.idUbicacion : 'N/A'}</p>
                    </div>
                    <div class="form-actions full-width">
                        <button type="submit" class="submit-button">Actualizar Contrato</button>
                    </div>
                </form>
            `;

            // Añadir listener al formulario para actualizar el contrato
            const contratoForm = document.getElementById('contrato-form');
            if (contratoForm) {
                contratoForm.addEventListener('submit', async (e) => {
                    e.preventDefault();
                    const updatedContrato = {
                        idContrato: contrato.idContrato, // Asegurarse de enviar el ID
                        precio: parseFloat(document.getElementById('precio').value),
                        duracion: document.getElementById('duracion').value,
                        tipo: document.getElementById('tipo').value,
                        fechaInicio: document.getElementById('fechaInicio').value, // Ya en YYYY-MM-DD
                        fechaFinal: document.getElementById('fechaFinal').value,   // Ya en YYYY-MM-DD
                        // Para evitar problemas de serialización, enviar solo los IDs de las relaciones
                        ubicacion: contrato.ubicacion ? { idUbicacion: contrato.ubicacion.idUbicacion } : null,
                        inquilino: contrato.inquilino ? { idPersona: contrato.inquilino.idPersona } : null,
                        idArrendadorNIF: contrato.idArrendadorNIF // Mantener el NIF del arrendador
                    };

                    try {
                        const response = await fetch(`${API_BASE_URL}/contratos/${contrato.idContrato}`, {
                            method: 'PUT',
                            headers: {
                                'Content-Type': 'application/json',
                            },
                            body: JSON.stringify(updatedContrato),
                        });

                        if (!response.ok) {
                            const errorData = await response.json();
                            throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
                        }
                        showAlert("Contrato actualizado exitosamente.", 'green');
                        renderContratoManagement(); // Recargar para mostrar los datos más recientes
                    } catch (err) {
                        console.error("Error al actualizar contrato:", err);
                        showAlert(`Error al actualizar contrato: ${err.message}`, 'red');
                    }
                });
            }

        } catch (err) {
            console.error("Error al cargar el contrato:", err);
            contratosPage.innerHTML = `<h2 class="section-title">Error al cargar Contrato</h2><p class="section-description alert-message red">${err.message}</p>`;
        }
    }

    // --- Gestión de Chats (Modal) ---

    /**
     * Abre el modal de chat y carga los datos.
     */
    async function openChatModal() {
        chatModal.classList.remove('hidden');
        document.body.style.overflow = 'hidden'; // Evitar scroll en el body

        // Limpiar mensajes y mostrar carga
        chatMessagesArea.innerHTML = '<p class="text-center text-gray-700">Cargando chat...</p>';
        chatWarning.classList.add('hidden'); // Ocultar advertencia inicialmente

        try {
            // 1. Obtener el contrato del inquilino para obtener el NIF del arrendador
            const contratoResponse = await fetch(`${API_BASE_URL}/contratos/inquilino/${currentUser.idPersona}`);
            if (!contratoResponse.ok) {
                if (contratoResponse.status === 404) {
                    chatMessagesArea.innerHTML = '<p class="text-center text-red-600">No se encontró un contrato asociado a este inquilino para acceder al chat.</p>';
                    return;
                }
                throw new Error(`HTTP error! status: ${contratoResponse.status}`);
            }
            const contratoData = await contratoResponse.json();
            const idArrendadorNIF = contratoData.idArrendadorNIF;

            // 2. Obtener el chat usando el NIF del arrendador
            const chatResponse = await fetch(`${API_BASE_URL}/chats/arrendador/${idArrendadorNIF}`);
            if (!chatResponse.ok) {
                if (chatResponse.status === 404) {
                    chatMessagesArea.innerHTML = '<p class="text-center text-red-600">No se encontró un chat para el arrendador de tu contrato.</p>';
                    return;
                }
                throw new Error(`HTTP error! status: ${chatResponse.status}`);
            }
            currentChat = await chatResponse.json(); // Almacenar el chat globalmente
            document.getElementById('chat-modal-title').textContent = `Mi Chat: ${currentChat.nombreChat}`;

            // 3. Obtener los mensajes de ese chat
            await fetchMessagesForChat(currentChat.idChat);

            // Verificar si el inquilino es el único participante
            if (currentChat.participantes && currentChat.participantes.length === 1 && currentChat.participantes[0].idPersona === currentUser.idPersona) {
                chatWarning.classList.remove('hidden');
            } else {
                chatWarning.classList.add('hidden');
            }

        } catch (err) {
            console.error("Error al obtener chat y mensajes:", err);
            chatMessagesArea.innerHTML = `<p class="text-center text-red-600">Error al cargar el chat: ${err.message}</p>`;
            chatWarning.classList.add('hidden');
        }
    }

    /**
     * Cierra el modal de chat.
     */
    function closeChatModal() {
        chatModal.classList.add('hidden');
        document.body.style.overflow = ''; // Restaurar scroll del body
        currentChat = null; // Limpiar el chat actual
        chatMessagesArea.innerHTML = ''; // Limpiar mensajes
        newMessageContentInput.value = ''; // Limpiar input
        chatWarning.classList.add('hidden'); // Ocultar advertencia
    }

    /**
     * Obtiene y renderiza los mensajes para un chat dado.
     * @param {Long} idChat El ID del chat.
     */
    async function fetchMessagesForChat(idChat) {
        try {
            const messagesResponse = await fetch(`${API_BASE_URL}/mensajes/chat/${idChat}`);
            if (!messagesResponse.ok) {
                throw new Error(`HTTP error! status: ${messagesResponse.status}`);
            }
            const messagesData = await messagesResponse.json();
            renderMessages(messagesData);
        } catch (err) {
            console.error("Error al obtener mensajes:", err);
            chatMessagesArea.innerHTML = `<p class="text-center text-red-600">Error al cargar mensajes: ${err.message}</p>`;
        }
    }

    /**
     * Renderiza los mensajes en el área de chat.
     * @param {Array<Object>} messages Los mensajes a renderizar.
     */
    function renderMessages(messages) {
        chatMessagesArea.innerHTML = ''; // Limpiar mensajes existentes
        if (messages.length === 0) {
            chatMessagesArea.innerHTML = '<p class="text-gray-600 text-center">No hay mensajes en este chat.</p>';
            return;
        }

        messages.forEach(msg => {
            const messageDiv = document.createElement('div');
            // Determinar si el mensaje es enviado por el usuario actual
            const isCurrentUser = msg.remitente.idPersona === currentUser.idPersona;
            messageDiv.className = `chat-message ${isCurrentUser ? 'sent' : 'received'}`;

            messageDiv.innerHTML = `
                <span class="chat-bubble">
                    <strong class="block text-sm">${msg.remitente.nombre}:</strong>
                    ${msg.contenido}
                    <span class="timestamp">${formatDateTimeForDisplay(msg.fechaEnvio)}</span>
                </span>
            `;
            chatMessagesArea.appendChild(messageDiv);
        });
        // Hacer scroll al final
        chatMessagesArea.scrollTop = chatMessagesArea.scrollHeight;
    }

    /**
     * Maneja el envío de un nuevo mensaje.
     * @param {Event} event El evento de submit del formulario.
     */
    async function handleSendMessage(event) {
        event.preventDefault();
        const content = newMessageContentInput.value.trim();

        if (!currentChat || !content) {
            showAlert("No hay chat seleccionado o el mensaje está vacío.", 'red');
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/mensajes/enviar`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idChat: currentChat.idChat,
                    idPersona: currentUser.idPersona,
                    contenido: content,
                }),
            });

            if (!response.ok) {
                const errorData = await response.json();
                throw new Error(errorData.message || `HTTP error! status: ${response.status}`);
            }

            newMessageContentInput.value = ''; // Limpiar el campo de entrada
            showAlert("Mensaje enviado exitosamente.", 'green');
            await fetchMessagesForChat(currentChat.idChat); // Recargar los mensajes para mostrar el nuevo
        } catch (err) {
            console.error("Error al enviar mensaje:", err);
            showAlert(`Error al enviar mensaje: ${err.message}`, 'red');
        }
    }

    // --- Inicialización ---

    // Cargar la información del usuario al cargar la página
    fetchCurrentUser().then(() => {
        // Una vez que el usuario se ha cargado, mostrar la página de inicio por defecto
        showPage('home-page');
    });

    // Añadir listeners al modal de chat
    closeChatModalButton.addEventListener('click', closeChatModal);
    chatForm.addEventListener('submit', handleSendMessage);
});


