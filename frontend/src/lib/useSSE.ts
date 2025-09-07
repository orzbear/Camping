import { useEffect, useRef, useState } from 'react';
import { WeatherWindow } from './types';

export const useSSE = (url: string) => {
  const [data, setData] = useState<WeatherWindow[]>([]);
  const [isConnected, setIsConnected] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const eventSourceRef = useRef<EventSource | null>(null);

  useEffect(() => {
    if (!url) return;

    const eventSource = new EventSource(url);
    eventSourceRef.current = eventSource;

    eventSource.onopen = () => {
      setIsConnected(true);
      setError(null);
    };

    eventSource.onmessage = (event) => {
      try {
        const parsedData = JSON.parse(event.data);
        setData(prev => [...prev, parsedData]);
      } catch (err) {
        console.error('Error parsing SSE data:', err);
      }
    };

    eventSource.addEventListener('plan_result', (event) => {
      try {
        const parsedData = JSON.parse(event.data);
        setData(prev => [...prev, parsedData]);
      } catch (err) {
        console.error('Error parsing plan result:', err);
      }
    });

    eventSource.addEventListener('plan_complete', () => {
      setIsConnected(false);
      eventSource.close();
    });

    eventSource.onerror = (event) => {
      console.error('SSE error:', event);
      setError('Connection error');
      setIsConnected(false);
    };

    return () => {
      eventSource.close();
    };
  }, [url]);

  const close = () => {
    if (eventSourceRef.current) {
      eventSourceRef.current.close();
      setIsConnected(false);
    }
  };

  return { data, isConnected, error, close };
};
