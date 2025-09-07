import React from 'react'
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import { Search } from './pages/Search'
import { Spot } from './pages/Spot'
import { Planner } from './pages/Planner'
import { Alerts } from './pages/Alerts'
import { Navigation } from './components/Navigation'

function App() {
  return (
    <Router>
      <div className="min-h-screen bg-secondary-50">
        <Navigation />
        <main className="container mx-auto px-4 py-8">
          <Routes>
            <Route path="/" element={<Search />} />
            <Route path="/spot/:id" element={<Spot />} />
            <Route path="/planner" element={<Planner />} />
            <Route path="/alerts" element={<Alerts />} />
          </Routes>
        </main>
      </div>
    </Router>
  )
}

export default App
