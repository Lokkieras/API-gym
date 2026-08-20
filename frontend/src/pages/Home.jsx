import { Link } from 'react-router-dom'
import './Home.css'

function Home() {
  return (
    <main className="home">
      <section className="home-hero">
        <h1 className="home-title">
          SEGUNDOS <span className="home-title-accent">FUERA</span>
        </h1>
        <p className="home-subtitle">
          Sistema de gestion de membresias para tu gimnasio
        </p>
        <div className="home-actions">
          <Link to="/registrar" className="home-btn home-btn-primary">
            Registrar Alumnos
          </Link>
          <Link to="/alumnos" className="home-btn home-btn-secondary">
            Ver Alumnos
          </Link>
        </div>
      </section>
    </main>
  )
}

export default Home
