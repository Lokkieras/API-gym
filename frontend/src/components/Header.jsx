import { Link } from 'react-router-dom'
import './Header.css'

function Header() {
  return (
    <header className="header">
      <Link to="/" className="header-logo">
        <img src="/Logo.png" alt="Segundos Fuera" className="header-logo-img" />
      </Link>
      <nav className="header-nav">
        <Link to="/registrar" className="header-nav-link">Registrar</Link>
        <Link to="/alumnos" className="header-nav-link">Alumnos</Link>
      </nav>
    </header>
  )
}

export default Header
