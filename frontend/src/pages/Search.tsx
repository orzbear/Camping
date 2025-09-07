import React, { useState, useEffect } from 'react';
import { spotApi } from '../lib/api';
import { SpotSummary } from '../lib/types';
import { SpotCard } from '../components/SpotCard';
import { Filters } from '../components/Filters';
import { Loader2 } from 'lucide-react';

export const Search: React.FC = () => {
  const [spots, setSpots] = useState<SpotSummary[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  
  // Filter states
  const [query, setQuery] = useState('');
  const [region, setRegion] = useState('');
  const [petAllowed, setPetAllowed] = useState<boolean | null>(null);

  const searchSpots = async () => {
    setLoading(true);
    setError(null);
    
    try {
      const response = await spotApi.search({
        query: query || undefined,
        region: region || undefined,
        petAllowed: petAllowed,
        page: 0,
        size: 20,
      });
      setSpots(response.spots);
    } catch (err) {
      setError('Failed to load campsites. Please try again.');
      console.error('Search error:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    searchSpots();
  }, []);

  return (
    <div className="max-w-7xl mx-auto">
      <div className="mb-8">
        <h1 className="text-3xl font-bold text-secondary-900 mb-2">
          Find Your Perfect Campsite
        </h1>
        <p className="text-secondary-600">
          Discover amazing camping spots with weather-aware recommendations
        </p>
      </div>

      <Filters
        query={query}
        setQuery={setQuery}
        region={region}
        setRegion={setRegion}
        petAllowed={petAllowed}
        setPetAllowed={setPetAllowed}
        onSearch={searchSpots}
      />

      {loading && (
        <div className="flex justify-center items-center py-12">
          <Loader2 className="h-8 w-8 animate-spin text-primary-600" />
          <span className="ml-2 text-secondary-600">Loading campsites...</span>
        </div>
      )}

      {error && (
        <div className="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
          <p className="text-red-800">{error}</p>
        </div>
      )}

      {!loading && !error && (
        <>
          <div className="mb-6">
            <p className="text-secondary-600">
              Found {spots.length} campsites
            </p>
          </div>

          {spots.length === 0 ? (
            <div className="text-center py-12">
              <p className="text-secondary-600 text-lg">
                No campsites found matching your criteria.
              </p>
              <p className="text-secondary-500 mt-2">
                Try adjusting your search filters.
              </p>
            </div>
          ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              {spots.map((spot) => (
                <SpotCard key={spot.id} spot={spot} />
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
};
