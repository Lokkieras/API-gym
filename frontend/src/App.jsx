import { Routes, Route } from 'react-router-dom'
import Header from './components/Header'
import Home from './pages/Home'
import Registrar from './pages/Registrar'
import Alumnos from './pages/Alumnos'
import './App.css'

function App() {
  return (
    <div className="app">
      <Header />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/registrar" element={<Registrar />} />
        <Route path="/alumnos" element={<Alumnos />} />
      </Routes>
    </div>
  )
}

export default App
