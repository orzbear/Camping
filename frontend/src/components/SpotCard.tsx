import React from 'react';
import { Link } from 'react-router-dom';
import { MapPin, DollarSign, PawPrint, Star } from 'lucide-react';
import { SpotSummary } from '../lib/types';

interface SpotCardProps {
  spot: SpotSummary;
}

export const SpotCard: React.FC<SpotCardProps> = ({ spot }) => {
  const getFeeDisplay = (fee: number) => {
    if (fee === 0) return 'Free';
    return `$${fee.toFixed(2)}`;
  };

  const getScoreColor = (score: number) => {
    if (score >= 0.8) return 'text-green-600 bg-green-100';
    if (score >= 0.6) return 'text-yellow-600 bg-yellow-100';
    return 'text-red-600 bg-red-100';
  };

  return (
    <Link to={`/spot/${spot.id}`} className="block">
      <div className="card hover:shadow-lg transition-shadow duration-200">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h3 className="text-lg font-semibold text-secondary-900 mb-1">
              {spot.name}
            </h3>
            <p className="text-sm text-secondary-600 mb-2">
              {spot.parkName} • {spot.region}
            </p>
          </div>
          <div className="flex items-center space-x-2">
            <span className={`px-2 py-1 rounded-full text-xs font-medium ${getScoreColor(0.8)}`}>
              <Star className="h-3 w-3 inline mr-1" />
              8.0
            </span>
          </div>
        </div>

        {spot.description && (
          <p className="text-sm text-secondary-700 mb-4 line-clamp-2">
            {spot.description}
          </p>
        )}

        <div className="flex items-center justify-between text-sm text-secondary-600">
          <div className="flex items-center space-x-4">
            <div className="flex items-center space-x-1">
              <DollarSign className="h-4 w-4" />
              <span>{getFeeDisplay(spot.feeAud)}</span>
            </div>
            {spot.petAllowed && (
              <div className="flex items-center space-x-1">
                <PawPrint className="h-4 w-4" />
                <span>Pet friendly</span>
              </div>
            )}
          </div>
          <div className="flex items-center space-x-1">
            <MapPin className="h-4 w-4" />
            <span>{spot.lat.toFixed(4)}, {spot.lon.toFixed(4)}</span>
          </div>
        </div>

        {spot.amenities.length > 0 && (
          <div className="mt-3 pt-3 border-t border-secondary-200">
            <div className="flex flex-wrap gap-1">
              {spot.amenities.slice(0, 3).map((amenity, index) => (
                <span
                  key={index}
                  className="px-2 py-1 bg-secondary-100 text-secondary-700 text-xs rounded-md"
                >
                  {amenity}
                </span>
              ))}
              {spot.amenities.length > 3 && (
                <span className="px-2 py-1 bg-secondary-100 text-secondary-700 text-xs rounded-md">
                  +{spot.amenities.length - 3} more
                </span>
              )}
            </div>
          </div>
        )}
      </div>
    </Link>
  );
};
