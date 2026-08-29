import { useState, useEffect } from 'react'
import './Alumnos.css'

function Alumnos() {
  const [users, setUsers] = useState([])
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

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
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </main>
  )
}

export default Alumnos
