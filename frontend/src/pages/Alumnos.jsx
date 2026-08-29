import { useState, useEffect } from 'react'
import './Alumnos.css'

function Alumnos() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [modal, setModal] = useState(null)
  const [actionLoading, setActionLoading] = useState(false)

  const loadUsers = async () => {
    setLoading(true)
    setError(null)
    try {
      const response = await fetch('/api/gym/bbdd')
      if (!response.ok) {
        const errText = await response.text()
        throw new Error(errText || 'Error al cargar alumnos')
      }
      setUsers(await response.json())
    } catch (err) {
      setError(err.message)
    } finally {
      setLoading(false)
    }
  }

  const handleDelete = async (id) => {
    setActionLoading(true)
    try {
      const response = await fetch(`/api/gym/${id}`, { method: 'DELETE' })
      if (response.status === 204) {
        throw new Error('El alumno no existe en el sistema')
      }
      if (!response.ok) {
        const errText = await response.text()
        throw new Error(errText || 'Error al eliminar el alumno')
      }
      setModal({ type: 'success', text: 'El alumno se ha eliminado correctamente' })
      loadUsers()
    } catch (err) {
      setModal({ type: 'error', text: err.message || 'Error al eliminar el alumno' })
    } finally {
      setActionLoading(false)
    }
  }

  const handleActivate = async (id) => {
    setActionLoading(true)
    try {
      const response = await fetch(`/api/gym/activate-by-dni/${id}`, { method: 'POST' })
      if (!response.ok) {
        const errText = await response.text()
        throw new Error(errText || 'Error al activar el alumno')
      }
      const result = await response.text()
      setModal({ type: 'success', text: `El alumno se ha activado correctamente. ${result}` })
      loadUsers()
    } catch (err) {
      setModal({ type: 'error', text: err.message || 'Error al activar el alumno' })
    } finally {
      setActionLoading(false)
    }
  }

  const closeModal = () => setModal(null)

  useEffect(() => {
    loadUsers()
  }, [])

  return (
    <main className="alumnos">
      <div className="alumnos-card">
        <h1 className="alumnos-title">
          <span className="alumnos-title-accent">VER</span> ALUMNOS
        </h1>

        {loading ? (
          <p className="alumnos-loading">Cargando alumnos...</p>
        ) : error ? (
          <div className="alumnos-error">
            <p className="alumnos-error-msg">{error}</p>
            <button className="alumnos-retry-btn" onClick={loadUsers}>Reintentar</button>
          </div>
        ) : users.length === 0 ? (
          <p className="alumnos-empty">No hay alumnos registrados</p>
        ) : (
          <div className="alumnos-table-container">
            <table className="alumnos-table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Apellido</th>
                  <th>DNI</th>
                  <th>Periodo</th>
                  <th>Día de Pago</th>
                  <th>Expiración</th>
                  <th>Estado</th>
                  <th>Acciones</th>
                </tr>
              </thead>
              <tbody>
                {users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.name}</td>
                    <td>{user.surname}</td>
                    <td>{user.dni}</td>
                    <td>{user.period}</td>
                    <td>{user.payDay}</td>
                    <td>{user.expirationDate}</td>
                    <td>
                      <span className={`alumnos-badge ${user.paid ? 'pagado' : 'no-pagado'}`}>
                        {user.paid ? 'Pagado' : 'No pagado'}
                      </span>
                    </td>
                    <td className="alumnos-actions">
                      <button
                        className="alumnos-btn alumnos-btn-delete"
                        onClick={() => handleDelete(user.id)}
                        disabled={actionLoading}
                      >
                        Eliminar
                      </button>
                      <button
                        className="alumnos-btn alumnos-btn-activate"
                        onClick={() => handleActivate(user.id)}
                        disabled={actionLoading}
                      >
                        Activar
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

      {modal && (
        <div className="alumnos-modal-overlay" onClick={closeModal}>
          <div className="alumnos-modal" onClick={e => e.stopPropagation()}>
            <div className={`alumnos-modal-icon ${modal.type}`}>
              {modal.type === 'success' ? '\u2713' : '\u2717'}
            </div>
            <p className="alumnos-modal-text">{modal.text}</p>
            <button className="alumnos-modal-btn" onClick={closeModal}>
              Aceptar
            </button>
          </div>
        </div>
      )}
    </main>
  )
}

export default Alumnos
