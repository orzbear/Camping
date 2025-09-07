import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { MapPin, Calendar, AlertTriangle, Search } from 'lucide-react';

export const Navigation: React.FC = () => {
  const location = useLocation();

  const navItems = [
    { path: '/', label: 'Search', icon: Search },
    { path: '/planner', label: 'Planner', icon: Calendar },
    { path: '/alerts', label: 'Alerts', icon: AlertTriangle },
  ];

  return (
    <nav className="bg-white shadow-sm border-b border-secondary-200">
      <div className="container mx-auto px-4">
        <div className="flex items-center justify-between h-16">
          <Link to="/" className="flex items-center space-x-2">
            <MapPin className="h-8 w-8 text-primary-600" />
            <span className="text-xl font-bold text-secondary-900">OutScout</span>
          </Link>
          
          <div className="flex space-x-8">
            {navItems.map(({ path, label, icon: Icon }) => (
              <Link
                key={path}
                to={path}
                className={`flex items-center space-x-2 px-3 py-2 rounded-md text-sm font-medium transition-colors duration-200 ${
                  location.pathname === path
                    ? 'text-primary-600 bg-primary-50'
                    : 'text-secondary-600 hover:text-secondary-900 hover:bg-secondary-50'
                }`}
              >
                <Icon className="h-4 w-4" />
                <span>{label}</span>
              </Link>
            ))}
          </div>
        </div>
      </div>
    </nav>
  );
};
