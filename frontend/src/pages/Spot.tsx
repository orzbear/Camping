import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { spotApi } from '../lib/api';
import { SpotDetails } from '../lib/types';
import { MapPin, DollarSign, PawPrint, ExternalLink, Calendar, Users } from 'lucide-react';
import { Loader2 } from 'lucide-react';

export const Spot: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const [spot, setSpot] = useState<SpotDetails | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!id) return;

    const fetchSpotDetails = async () => {
      setLoading(true);
      setError(null);
      
      try {
        const details = await spotApi.getDetails(parseInt(id));
        setSpot(details);
      } catch (err) {
        setError('Failed to load campsite details. Please try again.');
        console.error('Spot details error:', err);
      } finally {
        setLoading(false);
      }
    };

    fetchSpotDetails();
  }, [id]);

  if (loading) {
    return (
      <div className="flex justify-center items-center py-12">
        <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
        <span className="ml-2 text-secondary-600">Loading campsite details...</span>
      </div>
    );
  }

  if (error || !spot) {
    return (
      <div className="bg-red-50 border border-red-200 rounded-lg p-4">
        <p className="text-red-800">{error || 'Campsite not found'}</p>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900 mb-2">
          {spot.name}
        </h1>
        <p className="text-secondary-600">
          {spot.parkName} • {spot.region}
        </p>
      </div>

      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <div className="lg:col-span-2 space-y-6">
          <div className="card">
            <h2 className="text-xl font-semibold text-secondary-900 mb-4">
              About This Campsite
            </h2>
            {spot.description ? (
              <p className="text-secondary-700 leading-relaxed">
                {spot.description}
              </p>
            ) : (
              <p className="text-secondary-500 italic">
                No description available for this campsite.
              </p>
            )}
          </div>

          <div className="card">
            <h2 className="text-xl font-semibold text-secondary-900 mb-4">
              Amenities
            </h2>
            {spot.amenities.length > 0 ? (
              <div className="grid grid-cols-2 gap-3">
                {spot.amenities.map((amenity, index) => (
                  <div key={index} className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-primary-600 rounded-full"></div>
                    <span className="text-secondary-700">{amenity}</span>
                  </div>
                ))}
              </div>
            ) : (
              <p className="text-secondary-500 italic">
                No amenities information available.
              </p>
            )}
          </div>

          <div className="card">
            <h2 className="text-xl font-semibold text-secondary-900 mb-4">
              Weather Forecast
            </h2>
            <div className="bg-secondary-50 rounded-lg p-4 text-center">
              <p className="text-secondary-600">
                Weather forecast will be displayed here
              </p>
              <p className="text-sm text-secondary-500 mt-2">
                Coming soon: Real-time weather data integration
              </p>
            </div>
          </div>
        </div>

        <div className="space-y-6">
          <div className="card">
            <h2 className="text-xl font-semibold text-secondary-900 mb-4">
              Campsite Details
            </h2>
            
            <div className="space-y-4">
              <div className="flex items-center justify-between">
                <span className="text-secondary-600">Fee</span>
                <div className="flex items-center space-x-1">
                  <DollarSign className="h-4 w-4 text-secondary-500" />
                  <span className="font-medium">
                    {spot.feeAud === 0 ? 'Free' : `$${spot.feeAud.toFixed(2)}`}
                  </span>
                </div>
              </div>

              <div className="flex items-center justify-between">
                <span className="text-secondary-600">Pet Friendly</span>
                <div className="flex items-center space-x-1">
                  <PawPrint className="h-4 w-4 text-secondary-500" />
                  <span className="font-medium">
                    {spot.petAllowed ? 'Yes' : 'No'}
                  </span>
                </div>
              </div>

              <div className="flex items-center justify-between">
                <span className="text-secondary-600">Bookable</span>
                <span className={`font-medium ${spot.bookable ? 'text-green-600' : 'text-red-600'}`}>
                  {spot.bookable ? 'Yes' : 'No'}
                </span>
              </div>

              <div className="flex items-center justify-between">
                <span className="text-secondary-600">Authority</span>
                <span className="font-medium">{spot.authority}</span>
              </div>

              <div className="flex items-center justify-between">
                <span className="text-secondary-600">Location</span>
                <div className="flex items-center space-x-1">
                  <MapPin className="h-4 w-4 text-secondary-500" />
                  <span className="font-medium text-sm">
                    {spot.lat.toFixed(4)}, {spot.lon.toFixed(4)}
                  </span>
                </div>
              </div>
            </div>

            {spot.websiteUrl && (
              <div className="mt-6 pt-6 border-t border-secondary-200">
                <a
                  href={spot.websiteUrl}
                  target="_blank"
                  rel="noopener noreferrer"
                  className="btn-primary w-full flex items-center justify-center space-x-2"
                >
                  <ExternalLink className="h-4 w-4" />
                  <span>Visit Official Website</span>
                </a>
              </div>
            )}
          </div>

          <div className="card">
            <h2 className="text-xl font-semibold text-secondary-900 mb-4">
              Plan Your Trip
            </h2>
            <p className="text-secondary-600 mb-4">
              Use our smart planner to find the best weather windows for your visit.
            </p>
            <button className="btn-primary w-full flex items-center justify-center space-x-2">
              <Calendar className="h-4 w-4" />
              <span>Plan Trip</span>
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};
