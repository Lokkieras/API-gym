import { useState } from 'react'
import './Registrar.css'

function Registrar() {
  const [form, setForm] = useState({
    name: '',
    surname: '',
    age: '',
    dni: '',
    period: '',
  })

  const [errors, setErrors] = useState({
    name: '',
    surname: '',
    age: '',
    dni: '',
    period: '',
  })

  const [modal, setModal] = useState(null)
  const [loading, setLoading] = useState(false)

  const validate = () => {
    const newErrors = {}
    newErrors.name = form.name.trim() === '' ? 'El nombre es obligatorio' : ''
    newErrors.surname = form.surname.trim() === '' ? 'El apellido es obligatorio' : ''
    newErrors.age = form.age === '' ? 'La edad es obligatoria' : ''
    newErrors.dni = form.dni.trim() === '' ? 'El DNI es obligatorio' : ''
    newErrors.period = form.period === '' ? 'Selecciona un periodo' : ''

    setErrors(newErrors)
    return Object.values(newErrors).every(e => e === '')
  }

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value })
    if (errors[e.target.name]) {
      setErrors({ ...errors, [e.target.name]: '' })
    }
  }

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!validate()) return

    setLoading(true)

    try {
      const response = await fetch('/api/gym', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
          name: form.name,
          surname: form.surname,
          age: parseInt(form.age, 10),
          dni: form.dni,
          period: parseInt(form.period, 10),
        }),
      })

      if (!response.ok) {
        const errText = await response.text()
        throw new Error(errText || 'Error al registrar')
      }

      const data = await response.json()
      setModal({ type: 'success', text: `Alumno ${data.name} ${data.surname} registrado correctamente` })
    } catch (err) {
      setModal({ type: 'error', text: err.message })
    } finally {
      setLoading(false)
    }
  }

  const closeModal = () => {
    setModal(null)
    setForm({ name: '', surname: '', age: '', dni: '', period: '' })
    setErrors({ name: '', surname: '', age: '', dni: '', period: '' })
  }

  return (
    <main className="registrar">
      <div className="registrar-card">
        <h1 className="registrar-title">
          <span className="registrar-title-accent">REGISTRAR</span> ALUMNO
        </h1>

        <form className="registrar-form" onSubmit={handleSubmit} noValidate>
          <div className="registrar-field">
            <label className="registrar-label" htmlFor="name">Nombre</label>
            <input
              className={`registrar-input ${errors.name ? 'registrar-input-error' : ''}`}
              type="text"
              id="name"
              name="name"
              value={form.name}
              onChange={handleChange}
              placeholder="Ej: Juan"
            />
            {errors.name && <span className="registrar-error">{errors.name}</span>}
          </div>

          <div className="registrar-field">
            <label className="registrar-label" htmlFor="surname">Apellido</label>
            <input
              className={`registrar-input ${errors.surname ? 'registrar-input-error' : ''}`}
              type="text"
              id="surname"
              name="surname"
              value={form.surname}
              onChange={handleChange}
              placeholder="Ej: Perez"
            />
            {errors.surname && <span className="registrar-error">{errors.surname}</span>}
          </div>

          <div className="registrar-row">
            <div className="registrar-field">
              <label className="registrar-label" htmlFor="age">Edad</label>
              <input
                className={`registrar-input ${errors.age ? 'registrar-input-error' : ''}`}
                type="number"
                id="age"
                name="age"
                value={form.age}
                onChange={handleChange}
                placeholder="Ej: 25"
                min="1"
                max="120"
              />
              {errors.age && <span className="registrar-error">{errors.age}</span>}
            </div>

            <div className="registrar-field">
              <label className="registrar-label" htmlFor="dni">DNI</label>
              <input
                className={`registrar-input ${errors.dni ? 'registrar-input-error' : ''}`}
                type="text"
                id="dni"
                name="dni"
                value={form.dni}
                onChange={handleChange}
                placeholder="Ej: 12345678"
              />
              {errors.dni && <span className="registrar-error">{errors.dni}</span>}
            </div>
          </div>

          <div className="registrar-field">
            <label className="registrar-label" htmlFor="period">Periodo</label>
            <select
              className={`registrar-select ${errors.period ? 'registrar-input-error' : ''}`}
              id="period"
              name="period"
              value={form.period}
              onChange={handleChange}
            >
              <option value="" disabled>Seleccionar periodo</option>
              <option value="1">Mensual</option>
            </select>
            {errors.period && <span className="registrar-error">{errors.period}</span>}
          </div>

          <button
            className="registrar-btn"
            type="submit"
            disabled={loading}
          >
            {loading ? 'REGISTRANDO...' : 'REGISTRAR ALUMNO'}
          </button>
        </form>
      </div>

      {modal && (
        <div className="registrar-modal-overlay" onClick={closeModal}>
          <div className="registrar-modal" onClick={e => e.stopPropagation()}>
            <div className={`registrar-modal-icon ${modal.type}`}>
              {modal.type === 'success' ? '\u2713' : '\u2717'}
            </div>
            <p className="registrar-modal-text">{modal.text}</p>
            <button className="registrar-modal-btn" onClick={closeModal}>
              Aceptar
            </button>
          </div>
        </div>
      )}
    </main>
  )
}

export default Registrar
